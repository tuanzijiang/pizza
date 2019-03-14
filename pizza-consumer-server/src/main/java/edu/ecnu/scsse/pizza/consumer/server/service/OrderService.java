package edu.ecnu.scsse.pizza.consumer.server.service;

import com.google.gson.Gson;
import edu.ecnu.scsse.pizza.consumer.server.exception.*;
import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Pizza;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrdersResponse;
import edu.ecnu.scsse.pizza.consumer.server.utils.EntityConverter;
import edu.ecnu.scsse.pizza.consumer.server.utils.HttpUtils;
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

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final Gson GSON = new Gson();

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final AtomicInteger WORKER_COUNTER = new AtomicInteger();

    private static final long TEM_MIN = 10 * 60 * 1000;

    private static final String SERVICE_PHONE = "021-9999-9999";

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

    @Autowired
    private DriverJpaRepository driverJpaRepository;

    @Autowired
    private PizzaShopJpaRepository pizzaShopJpaRepository;

    /**
     * Query order by order uuid.
     *
     * @param orderId order uuid.
     * @return order
     */
    public Order fetchOrder(String orderId) throws ConsumerServerException {
        Optional<OrderEntity> orderEntityOptional = orderJpaRepository.findByOrderUuid(orderId);
        if (orderEntityOptional.isPresent()){
            OrderEntity entity = orderEntityOptional.get();
            Order order = EntityConverter.convert(entity);
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

            return order;
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
    public List<Order> fetchOrders(Integer userId, List<OrderStatus> orderStatuses, String lastOrderUuid, Integer count) throws NotFoundException, IllegalArgumentException {
        // arg check
        if (userId == null) {
            throw new IllegalArgumentException("userId can't be null.");
        }
        if (CollectionUtils.isEmpty(orderStatuses)) {
            throw new IllegalArgumentException("orderStatuses can't be empty.");
        }
        if (!this.isPositive(count)) {
            throw new IllegalArgumentException("count must be positive.");
        }

        // translate order statues values in db.
        List<Integer> orderStatuesValue = orderStatuses.stream()
                .mapToInt(OrderStatus::getDbValue)
                .boxed().collect(Collectors.toList());
        if (orderStatuses.isEmpty()) {
            throw new NotFoundException(String.format("Can't find relevant state in db with orderStatus {%s}.",
                    GSON.toJson(orderStatuses.toString())));
        }

        Optional<Integer> lastOrderId = lastOrderUuid == null ?  Optional.of(0):
                orderJpaRepository.findIdByOrderUuid(lastOrderUuid);
        if (lastOrderId.isPresent()) {
            List<OrderEntity> orderEntityList = orderJpaRepository.findByUserIdAndStateInAndIdGreaterThan(
                    userId, orderStatuesValue, lastOrderId.get(), PageRequestFactory.create().count(count).build());
            return this.convert(orderEntityList);
        } else {
            throw new NotFoundException("There is no order where 'orderUuid'={%s}", lastOrderUuid);
        }
    }

    /**
     * Query order by userId and order status.
     *
     * @param userId user id
     * @return new cart order
     */
    public Order getCartOrder(Integer userId) throws IllegalArgumentException {
        if (userId == null) {
            throw new IllegalArgumentException("userId must not be NULL.");
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setState(OrderStatus.CART.getDbValue());
        orderEntity.setUserId(userId);
        orderEntity.setOrderUuid(UUID.randomUUID().toString());

        orderEntity = orderJpaRepository.save(orderEntity);
        return EntityConverter.convert(orderEntity);
    }

    /**
     * Query pizza menus.
     * 
     * @return pizza menus.
     */
    public List<Pizza> getAllMenu() {
        return menuJpaRepository.findAll().stream().map(EntityConverter::convert).collect(Collectors.toList());
    }

    /**
     * Update order.
     *
     * @param orderUuid orderUuid
     * @param menuId menuId
     * @param count count
     * @throws NotFoundException
     * @throws IllegalArgumentException
     */
    public void updateOrder(String orderUuid, Integer menuId, Integer count) throws NotFoundException, IllegalArgumentException {
        if (orderUuid == null || !this.isPositive(menuId) || !this.isPositive(count)) {
            throw new IllegalArgumentException("IllegalArgument orderUuid[%s], menuId[%s], count[%s]", orderUuid, menuId, count);
        }
        Integer orderId = orderJpaRepository.findCartIdByOrderUuid(orderUuid)
                .orElseThrow(() -> new NotFoundException("Fail to find cart order with orderUuid=[%s]", orderUuid));
        OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
        orderMenuEntity.setOrderId(orderId);
        orderMenuEntity.setMenuId(menuId);
        orderMenuEntity.setCount(count);
        orderMenuJpaRepository.save(orderMenuEntity);
    }

    /**
     * Cancel order.
     *
     * @param orderUuid orderUuid
     */
    public boolean cancelOrder(String orderUuid) throws NotFoundException {
        OrderEntity order = orderJpaRepository.findByOrderUuid(orderUuid)
                .orElseThrow(() -> new NotFoundException("No order found with orderUuid[%s]", orderUuid));
        if (this.canOrderCancel(order)) {
            orderJpaRepository.updateStateByOrderUuid(OrderStatus.CANCEL_CHECKING.getDbValue(), orderUuid);
            return true;
        }
        return false;
    }

    /**
     * Get phones.
     *
     * @param orderUuid orderUuid
     * @return phones
     * @throws NotFoundException
     */
    public Phones getPhones(String orderUuid) throws NotFoundException {
        OrderEntity order = orderJpaRepository.findByOrderUuid(orderUuid)
                .orElseThrow(() -> new NotFoundException("No order found with orderUuid[%s]", orderUuid));
        Phones phones = new Phones();
        if (this.isPositive(order.getDriverId())) {
            DriverEntity driverEntity = driverJpaRepository.findById(order.getDriverId())
                    .orElseThrow(() -> new NotFoundException("No driver found with id[%s]", order.getDriverId()));
            phones.deliverymanPhone = driverEntity.getPhone();
        }

        if (this.isPositive(order.getShopId())) {
             PizzaShopEntity shopEntity = pizzaShopJpaRepository.findById(order.getShopId())
                    .orElseThrow(() -> new NotFoundException("No shop found with id[%s]", order.getShopId()));
            phones.shopPhone = shopEntity.getPhone();
        }
        return phones;
    }

    /**
     * Send an order to business service.
     * 
     * @param orderUuid
     * @param userAddressId
     * @return
     * @throws IllegalArgumentException
     * @throws ServiceException
     */
    public Order sendOrder(String orderUuid, Integer userAddressId) throws IllegalArgumentException, ServiceException {
        // arg check
        if (orderUuid == null) {
            throw new IllegalArgumentException("orderUuid can't be null.");
        }
        if (!this.isPositive(userAddressId)) {
            throw new IllegalArgumentException("userAddressId must be positive.");
        }

        OrderEntity orderEntity = null;
        try {
            orderEntity = HttpUtils.commitOrder(orderUuid, userAddressId);
        } catch (IOException e) {
            throw new ServiceException("I/O Exception while sending http request to Business Server.", e);
        }
        return EntityConverter.convert(orderEntity);
    }



    public static class Phones {
        private String servicePhone = SERVICE_PHONE;
        private String deliverymanPhone;
        private String shopPhone;

        public String getServicePhone() {
            return servicePhone;
        }

        public void setServicePhone(String servicePhone) {
            this.servicePhone = servicePhone;
        }

        public String getDeliverymanPhone() {
            return deliverymanPhone;
        }

        public void setDeliverymanPhone(String deliverymanPhone) {
            this.deliverymanPhone = deliverymanPhone;
        }

        public String getShopPhone() {
            return shopPhone;
        }

        public void setShopPhone(String shopPhone) {
            this.shopPhone = shopPhone;
        }
    }


    // private methods

    private boolean canOrderCancel(OrderEntity order) {
        if (order.getCommitTime() != null) {
            long interval = System.currentTimeMillis() - order.getCommitTime().getTime();
            return interval <= TEM_MIN;
        }
        log.error("Find an uncommitted order while canceling order, orderId=[]", order.getId());
        return false;
    }

    private boolean isPositive(Integer num) {
        return num != null && num > 0;
    }

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
                Order order = EntityConverter.convert(e);
                OrderMenuEntity om = omMapping.get(e.getId());
                Set<Integer> pizzaIds = mMapping.get(e.getId());
                List<Pizza> pizzas = menuEntities.stream()
                        .filter(m -> pizzaIds.contains(m.getId()))
                        .map(m -> EntityConverter.convert(om, m))
                        .collect(Collectors.toList());
                order.setPizzas(pizzas);
                result.add(order);
            });

            return result;
        }

        return Collections.EMPTY_LIST;
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
            addressEntityOptional.ifPresent(addressEntity -> order.setAddress(EntityConverter.convert(userAddressEntity, addressEntity)));

            if (addressEntityOptional.isPresent()) {
                AddressEntity addressEntity = addressEntityOptional.get();
                order.setAddress(EntityConverter.convert(userAddressEntity, addressEntity));
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
                        .ifPresent(m -> pizzas.add(EntityConverter.convert(om, m)));
            });
            order.setPizzas(pizzas);
        }
    }

    

}
