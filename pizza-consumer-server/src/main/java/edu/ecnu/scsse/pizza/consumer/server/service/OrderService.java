package edu.ecnu.scsse.pizza.consumer.server.service;

import com.google.gson.Gson;
import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.exception.ExceptionType;
import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Pizza;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrdersResponse;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.*;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrderResponse;
import edu.ecnu.scsse.pizza.data.repository.*;
import edu.ecnu.scsse.pizza.data.util.PageRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final Gson GSON = new Gson();

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

    /**
     * Query order by order uuid.
     *
     * @param orderId order uuid.
     * @return order
     */
    public FetchOrderResponse fetchOrder(String orderId) throws ConsumerServerException {
        Optional<OrderEntity> orderEntityOptional = orderJpaRepository.findByOrderUuid(orderId);
        if (orderEntityOptional.isPresent()){
            OrderEntity entity = orderEntityOptional.get();
            FetchOrderResponse response = new FetchOrderResponse();
            Order order = this.convert(entity);
            // query Address
            Future queryFuture = WORKER.submit(() -> this.supplementAddress(entity, order));
            // query Pizzas
            this.supplementPizzas(entity, order);

            try {
                queryFuture.get(1, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new ConsumerServerException(ExceptionType.REPOSITORY,
                        "Fail to query Address while assembling the order entity."
                        , e);
            }

            response.setOrder(order);
            return response;
        } else {
            throw new NotFoundException(String.format("Order with order id %s is not found.", orderId));
        }
    }

    /**
     * Query orders by userId and order status.
     *
     * @param userId userId
     * @param orderStatuses order status list
     * @return order
     */
    public FetchOrdersResponse fetchOrders(Integer userId, List<OrderStatus> orderStatuses, String lastOrderUuid, Integer count) throws NotFoundException, IllegalArgumentException {
        // arg check
        if (userId == null) {
            throw new IllegalArgumentException("userId can't be null.", null);
        }
        if (CollectionUtils.isEmpty(orderStatuses)) {
            throw new IllegalArgumentException("orderStatuses can't be empty.", null);
        }
        if (count == null || count < 0) {
            throw new IllegalArgumentException("count must be positive.", null);
        }

        // translate order statues values in db.
        List<Integer> orderStatuesValue = orderStatuses.stream()
                .mapToInt(OrderStatus::getDbValue)
                .boxed().collect(Collectors.toList());
        if (orderStatuses.isEmpty()) {
            throw new NotFoundException(String.format("Can't find relevant state in db with orderStatus {%s}.",
                    GSON.toJson(orderStatuses.toString())));
        }

        FetchOrdersResponse response = new FetchOrdersResponse();
        Optional<Integer> lastOrderId = lastOrderUuid == null ?  Optional.of(0):
                orderJpaRepository.findIdByOrderUuid(lastOrderUuid);
        if (lastOrderId.isPresent()) {
            List<OrderEntity> orderEntityList = orderJpaRepository.findByUserIdAndStateInAndIdGreaterThan(
                    userId, orderStatuesValue, lastOrderId.get(), PageRequestFactory.create().count(count).build());
            response.setOrders(this.convert(orderEntityList));
        } else {
            throw new NotFoundException("There is no order where 'orderUuid'={%s}", lastOrderUuid);
        }

        return response;
    }

    /**
     * Query order by userId and order status.
     *
     * @param userId user id
     * @return new cart order
     */
    public Order getCartOrder(Integer userId) throws IllegalArgumentException {
        if (userId == null) {
            throw new IllegalArgumentException("userId must not be NULL.", null);
        }

        Optional<OrderEntity> cart = orderJpaRepository.findFirstByUserIdAndStateOrderByIdDesc(
                userId, OrderStatus.CART.getDbValue());
        OrderEntity orderEntity = null;
        if (!cart.isPresent()) {
            orderEntity = new OrderEntity();
            orderEntity.setState(OrderStatus.CART.getDbValue());
            orderEntity.setUserId(userId);
            orderEntity.setOrderUuid(UUID.randomUUID().toString());

            orderEntity = orderJpaRepository.save(orderEntity);
        } else {
            orderEntity = cart.get();
        }

        return this.convert(orderEntity);
    }

    /**
     * Query pizza menus.
     *
     * @return pizza menus.
     */
    public List<Pizza> getAllMenu() {
        return menuJpaRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
    }



    // private methods

    /**
     * Assemble orders with out address info.
     *
     * @param entityList order db entity list
     * @return orders
     */
    private List<Order> convert(List<OrderEntity> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.EMPTY_LIST;
        }

        List<Integer> orderIds = entityList.stream().mapToInt(OrderEntity::getId).boxed().collect(Collectors.toList());

        // query Pizza
        List<OrderMenuEntity> orderMenuEntities = orderMenuJpaRepository.findByOrderIdIn(orderIds);
        if (!CollectionUtils.isEmpty(orderMenuEntities)) {
            Set<Integer> menuIds = new HashSet<>();
            Map<Integer, OrderMenuEntity> omMapping = new HashMap<>();
            Map<Integer, Set<Integer>> mMapping = new HashMap<>();

            orderMenuEntities.forEach(om -> {
                menuIds.add(om.getOrderId());
                omMapping.put(om.getOrderId(), om);
                mMapping.computeIfAbsent(om.getOrderId(), k -> new HashSet<>()).add(om.getMenuId());
            });

            List<MenuEntity> menuEntities = menuJpaRepository.findAllById(menuIds);

            List<Order> result = new ArrayList<>();
            entityList.forEach(e -> {
                Order order = this.convert(e);
                OrderMenuEntity om = omMapping.get(e.getId());
                Set<Integer> pizzaIds = mMapping.get(e.getId());
                List<Pizza> pizzas = menuEntities.stream()
                        .filter(m -> pizzaIds.contains(m.getId()))
                        .map(m -> this.convert(om, m))
                        .collect(Collectors.toList());
                order.setPizzas(pizzas);
                result.add(order);
            });

            return result;
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * Assemble basic order info.
     *
     * @param entity order db entity.
     * @return Order
     */
    private Order convert(OrderEntity entity) {
        Order order = new Order();
        order.setId(entity.getOrderUuid());
        order.setStatus(OrderStatus.fromDbValue(entity.getState()));

        if (entity.getCommitTime() != null) {
            order.setStartTime(entity.getCommitTime().getTime());
        }
        return order;
    }

    /**
     * Query and supplement address info to {@param order}.
     *
     * @param entity order db entity.
     * @param order order to supplement.
     */
    private void supplementAddress(OrderEntity entity, Order order) {
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
    }

    /**
     * Query and supplement order menu info to {@param order}.
     *
     * @param entity order db entity.
     * @param order order to supplement.
     */
    private void supplementPizzas(OrderEntity entity, Order order) {
        List<OrderMenuEntity> orderMenuEntities = orderMenuJpaRepository.findByOrderId(entity.getId());
        if (!CollectionUtils.isEmpty(orderMenuEntities)) {
            List<MenuEntity> menuEntities = menuJpaRepository.findAllById(orderMenuEntities.stream()
                    .mapToInt(OrderMenuEntity::getMenuId)
                    .boxed().collect(Collectors.toSet()));
            List<Pizza> pizzas = new ArrayList<>();
            orderMenuEntities.forEach( om -> {
                menuEntities.stream()
                        .filter(m -> Objects.equals(m.getId(), om.getMenuId()))
                        .findFirst()
                        .ifPresent(m -> pizzas.add(this.convert(om, m)));
            });
            order.setPizzas(pizzas);
        }
    }

    /**
     * Construct Address.
     *
     * @param userAddressEntity userAddressEntity
     * @param addressEntity addressEntity
     * @return Address
     */
    private Address convert(UserAddressEntity userAddressEntity, AddressEntity addressEntity) {
        Address address = new Address();

        address.setId(address.getId());
        address.setAddress(addressEntity.getAddress());

        address.setAddressDetail(userAddressEntity.getAddressDetail());
        address.setName(userAddressEntity.getName());
        address.setPhone(userAddressEntity.getPhone());

        address.setSex(Sex.fromDbValue(userAddressEntity.getSex()));

        return address;
    }

    /**
     * Construct Pizza Menu.
     *
     * @param menuEntity menuEntity
     * @return Pizza
     */
    private Pizza convert(MenuEntity menuEntity) {
        return this.convert(null, menuEntity);
    }

    /**
     * Construct Pizza.
     *
     * @param orderMenuEntity orderMenuEntity
     * @param menuEntity menuEntity
     * @return Pizza
     */
    private Pizza convert(OrderMenuEntity orderMenuEntity, MenuEntity menuEntity) {
        Pizza pizza = new Pizza();

        if (orderMenuEntity != null) {
            pizza.setCount(orderMenuEntity.getCount());
        }

        if (menuEntity != null) {
            pizza.setId(menuEntity.getId());
            pizza.setName(menuEntity.getName());
            pizza.setDescription(menuEntity.getDescription());
            pizza.setImg(menuEntity.getImage());
            pizza.setPrice(menuEntity.getPrice());
            pizza.setState(PizzaStatus.fromDbValue(menuEntity.getState()));
            pizza.setTag(PizzaTag.fromDbValue(menuEntity.getTag()).getExpression());
        }

        return pizza;
    }
}
