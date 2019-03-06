package edu.ecnu.scsse.pizza.consumer.server.service;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.exception.ExceptionType;
import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Pizza;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.AddressTag;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import edu.ecnu.scsse.pizza.data.enums.Sex;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrderResponse;
import edu.ecnu.scsse.pizza.data.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final AtomicInteger WORKER_COUNTER = new AtomicInteger();

    private final ExecutorService WORKER = Executors.newFixedThreadPool(8, r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName("order-service-query-worker-" + WORKER_COUNTER.incrementAndGet());
        return thread;
    });

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private UserAddressJpaRepository userAddressJpaRepository;

    @Autowired
    private AddressJpaRepository addressJpaRepository;

    @Autowired
    private OrderMenuJpaRepository orderMenuJpaRepository;

    @Autowired
    private MenuJpaRepository menuJpaRepository;

    public FetchOrderResponse fetchOrder(String orderId) {
        FetchOrderResponse response = null;
        Optional<OrderEntity> orderEntity = orderJpaRepository.findByOrderUuid(orderId);
        if (orderEntity.isPresent()){
            try {
                response = new FetchOrderResponse();
                response.setOrder(this.convert(orderEntity.get()));
            } catch (ConsumerServerException e) {
                response = new FetchOrderResponse(e);
                log.error("Fail to find the order with orderId {}.", orderId, e);
            }
        } else {
            NotFoundException e = new NotFoundException(String.format("Order with order id %s is not found.", orderId));
            response = new FetchOrderResponse();
            log.warn("Fail to find the order with orderId {}.", orderId, e);
        }

        return response;
    }

    public FetchOrderResponse createCartOrder(Integer userId) {
        FetchOrderResponse response = new FetchOrderResponse();
        if (userId == null) {
            response.setResultType(ResultType.FAILURE);
            IllegalArgumentException e = new IllegalArgumentException("userId must not be NULL.", null);
            response.setCause(e);
            response.setErrorMsg(e.getHintMessage());
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setState(OrderStatus.CART.getDbValue());
        orderEntity.setUserId(userId);
        orderEntity.setOrderUuid(UUID.randomUUID().toString());

        orderEntity = orderJpaRepository.save(orderEntity);
        try {
            response.setOrder(this.convert(orderEntity));
        } catch (ConsumerServerException e) {
            response = new FetchOrderResponse(e);
            log.error("Fail to create cart order with userId {}.", userId, e);
        }

        return response;
    }



    private Order convert(OrderEntity entity) throws ConsumerServerException {
        Order order = new Order();
        order.setId(entity.getOrderUuid());
        order.setStatus(OrderStatus.fromDbValue(entity.getState()));

        if (entity.getCommitTime() != null) {
            order.setStartTime(entity.getCommitTime().getTime());
        }

        // query Address
        Future queryFuture = WORKER.submit(() -> {
            Optional<UserAddressEntity> userAddressEntityOptional =
                    userAddressJpaRepository.findByUserIdAndAddressId(entity.getUserId(), entity.getAddressId());
            if (userAddressEntityOptional.isPresent()) {
                UserAddressEntity userAddressEntity = userAddressEntityOptional.get();

                Optional<AddressEntity> addressEntityOptional =
                        addressJpaRepository.findById(userAddressEntity.getAddressId());
                addressEntityOptional.ifPresent(addressEntity -> order.setAddress(this.convert(userAddressEntity, addressEntity)));

                if (addressEntityOptional.isPresent()) {
                    AddressEntity addressEntity = addressEntityOptional.get();
                    order.setAddress(this.convert(userAddressEntity, addressEntity));
                }
            }
        });


        // query Pizza
        List<OrderMenuEntity> orderMenuEntities = orderMenuJpaRepository.findByOrderId(entity.getId());
        if (!CollectionUtils.isEmpty(orderMenuEntities)) {
            List<MenuEntity> menuEntities = menuJpaRepository.findAllById(orderMenuEntities.stream()
                                                .mapToInt(OrderMenuEntity::getMenuId)
                                                .boxed().collect(Collectors.toList()));
            List<Pizza> pizzas = new ArrayList<>();
            orderMenuEntities.forEach( om -> {
                menuEntities.stream()
                        .filter(m -> Objects.equals(m.getId(), om.getMenuId()))
                        .findFirst()
                        .ifPresent(m -> pizzas.add(this.convert(om, m)));
            });
            order.setPizzas(pizzas);
        }

        try {
            queryFuture.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new ConsumerServerException(ExceptionType.REPOSITORY,
                    "Fail to query Address while assembling the order entity."
                    , e);
        }

        return order;
    }

    private Address convert(UserAddressEntity userAddressEntity, AddressEntity addressEntity) {
        Address address = new Address();

        address.setId(address.getId());
        address.setAddress(addressEntity.getAddress());

        address.setAddressDetail(userAddressEntity.getAddressDetail());
        address.setName(userAddressEntity.getName());
        address.setPhone(userAddressEntity.getPhone());

        address.setSex(Sex.fromDbValue(userAddressEntity.getSex()));
        AddressTag addressTag = AddressTag.fromDbValue(userAddressEntity.getTag());
        if (addressTag != null) {
            address.setTag(addressTag.getExpression());
        }

        return address;
    }

    private Pizza convert(OrderMenuEntity orderMenuEntity, MenuEntity menuEntity) {
        Pizza pizza = new Pizza();

        pizza.setCount(orderMenuEntity.getCount());

        pizza.setId(menuEntity.getId());
        pizza.setName(menuEntity.getName());
        pizza.setDescription(menuEntity.getDescription());
        pizza.setImg(menuEntity.getImage());
        pizza.setPrice(menuEntity.getPrice());
        pizza.setState(PizzaStatus.fromDbValue(menuEntity.getState()));
        // pizza.setTag(menuEntity.getTag());

        return pizza;
    }
}
