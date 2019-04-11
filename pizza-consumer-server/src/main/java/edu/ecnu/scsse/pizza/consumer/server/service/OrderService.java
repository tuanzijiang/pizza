package edu.ecnu.scsse.pizza.consumer.server.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.google.gson.Gson;
import edu.ecnu.scsse.pizza.consumer.server.exception.*;
import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.model.PayType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Pizza;
import edu.ecnu.scsse.pizza.consumer.server.utils.AlipayConfig;
import edu.ecnu.scsse.pizza.consumer.server.utils.EntityConverter;
import edu.ecnu.scsse.pizza.consumer.server.utils.HttpUtils;
import edu.ecnu.scsse.pizza.data.bean.PizzaBean;
import edu.ecnu.scsse.pizza.data.bean.UserAddressBean;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.*;
import edu.ecnu.scsse.pizza.data.repository.*;
import edu.ecnu.scsse.pizza.data.util.PageRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final AtomicInteger WORKER_COUNTER = new AtomicInteger();

    private static final long TEM_MIN = 10 * 60 * 1000;

    private static final String SERVICE_PHONE = "021-9999-9999";

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private UserAddressJpaRepository userAddressJpaRepository;

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
            if (order.getStatus() != OrderStatus.CART) {
                // query Address
                this.supplementAddress(entity, order);
                // query Pizzas
                this.supplementPizzas(entity, order, order.getStatus() == OrderStatus.CART);
            } else {
                // query Pizzas
                this.supplementPizzas(entity, order, order.getStatus() == OrderStatus.CART);
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
    public List<Order> fetchOrders(Integer userId, List<OrderStatus> orderStatuses, String lastOrderUuid, Integer count) throws ConsumerServerException {
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

        Integer lastOrderId = lastOrderUuid == null || lastOrderUuid.isEmpty() ? -1:
                orderJpaRepository.findIdByOrderUuid(lastOrderUuid)
                        .orElseThrow(() -> new NotFoundException("There is no order where 'orderUuid'={%s}", lastOrderUuid));
        List<OrderEntity> orderEntityList = orderJpaRepository.findByUserIdAndStateInAndIdGreaterThan(
                userId, orderStatuesValue,
                lastOrderId,
                PageRequestFactory.create().count(count).asc("id").build());
        return this.convert(orderEntityList);
    }

    /**
     * Query order by userId and order status.
     *
     * @param userId user id
     * @return new cart order
     */
    public Order getCartOrder(Integer userId, List<Pizza> menus) throws ConsumerServerException {
        if (userId == null) {
            throw new IllegalArgumentException("userId must not be NULL.");
        }

        OrderEntity orderEntity;
        Order result;
        Optional<OrderEntity> optionalOrderEntity = orderJpaRepository.
                findFirstByUserIdAndStateOrderByIdDesc(userId, OrderStatus.CART.getDbValue());
        if (optionalOrderEntity.isPresent()) {
            orderEntity = optionalOrderEntity.get();
            result = EntityConverter.convert(orderEntity);
            this.supplementPizzas(orderEntity, result, menus);
        } else {
            orderEntity = new OrderEntity();
            orderEntity.setState(OrderStatus.CART.getDbValue());
            orderEntity.setUserId(userId);
            orderEntity.setOrderUuid(UUID.randomUUID().toString());
            orderJpaRepository.save(orderEntity);
            result = EntityConverter.convert(orderEntity);
        }
        return result;
    }




    /**
     * Query pizza menus.
     * 
     * @return pizza menus.
     */
    public List<Pizza> getInSaleMenu() {
        return menuJpaRepository.findAllByState(PizzaStatus.IN_SALE.getDbValue())
                .stream().map(EntityConverter::convert).collect(Collectors.toList());
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
    public int updateOrder(String orderUuid, Integer menuId, Integer count) throws ConsumerServerException {
        if (orderUuid == null || !this.isPositive(menuId) || !this.isPositive(count)) {
            throw new IllegalArgumentException("IllegalArgument orderUuid[%s], menuId[%s], count[%s]", orderUuid, menuId, count);
        }
        Integer orderId = orderJpaRepository.findCartIdByOrderUuid(orderUuid)
                .orElseThrow(() -> new NotFoundException("Fail to find cart order with orderUuid=[%s]", orderUuid));

        return orderMenuJpaRepository.updateCount(count, orderId, menuId);
    }

    /**
     * Cancel order.
     *
     * @param orderUuid orderUuid
     */
    public boolean cancelOrder(String orderUuid) throws ConsumerServerException {
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
    public Phones getPhones(String orderUuid) throws ConsumerServerException {
        OrderEntity order = orderJpaRepository.findByOrderUuid(orderUuid)
                .orElseThrow(() -> new NotFoundException("No order found with orderUuid[%s]", orderUuid));
        Phones phones = new Phones();
        DriverEntity driverEntity = driverJpaRepository.findById(order.getDriverId())
                .orElseThrow(() -> new NotFoundException("No driver found with id[%s]", order.getDriverId()));
        phones.deliverymanPhone = driverEntity.getPhone();

        PizzaShopEntity shopEntity = pizzaShopJpaRepository.findById(order.getShopId())
                .orElseThrow(() -> new NotFoundException("No shop found with id[%s]", order.getShopId()));
        phones.shopPhone = shopEntity.getPhone();
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
    public Order sendOrder(String orderUuid, Integer userAddressId) throws ConsumerServerException {
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


    public String payRequest(String orderUuid, double totalPrice, PayType type) throws ConsumerServerException {
        // arg check
        if (orderUuid == null) {
            throw new IllegalArgumentException("orderUuid can't be null.");
        }
        if (!this.isPositive(totalPrice)) {
            throw new IllegalArgumentException("totalPrice must be positive.");
        }

        DefaultAlipayClient client = new DefaultAlipayClient(AlipayConfig.GATE_WAY, AlipayConfig.APP_ID, AlipayConfig.PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

        AlipayRequest request;
        switch (type) {
            case PC: request = this.pcRequest(orderUuid, totalPrice);break;
            case MOBILE: request = this.mobileRequest(orderUuid, totalPrice); break;
            default:
                throw new IllegalArgumentException(String.format(
                        "Illeagal pay type: [%s]", type == null ? "NULL" : type.name()));
        }
        try {
            AlipayResponse response = client.pageExecute(request);
            if (!response.isSuccess()) {
                PayFailureException payFailureException = new PayFailureException(response.getMsg());
                log.warn("Pay Failure. orderUuid = [{}].", orderUuid, payFailureException);
                throw payFailureException;
            }

            orderJpaRepository.updateStateAndTotalPriceByOrderUuid(OrderStatus.PAID.getDbValue(),
                    totalPrice, orderUuid);
            return response.getBody();
        } catch (AlipayApiException e) {
            PayFailureException payFailureException =  new PayFailureException(e);
            log.error("Pay Failure. orderUuid = [{}].", orderUuid, payFailureException);
            throw payFailureException;
        }
    }

    private AlipayTradeWapPayRequest mobileRequest (String orderUuid, double totalPrice) {
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(orderUuid);
        model.setSubject(AlipayConfig.SUBJECT);
        model.setTotalAmount(String.valueOf(totalPrice));
        model.setProductCode(AlipayConfig.PRODUCT_CODE);
        alipayRequest.setBizModel(model);
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        return alipayRequest;
    }

    private AlipayTradePagePayRequest pcRequest (String orderUuid, double totalPrice) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // 封装请求支付信息
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(orderUuid);
        model.setSubject(AlipayConfig.SUBJECT);
        model.setTotalAmount(String.valueOf(totalPrice));
        model.setProductCode(AlipayConfig.PRODUCT_CODE);
        request.setReturnUrl(AlipayConfig.RETURN_URL);
        request.setBizModel(model);
        return request;
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

    private boolean isPositive(Double num) {
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
                menuIds.add(om.getMenuId());
                omMapping.put(om.getOrderId(), om);
                mMapping.computeIfAbsent(om.getOrderId(), k -> new HashSet<>()).add(om.getMenuId());
            });

            List<MenuEntity> menuEntities = menuJpaRepository.findAllById(menuIds);

            List<Order> result = new ArrayList<>();
            entityList.forEach(e -> {
                Order order = EntityConverter.convert(e);
                OrderMenuEntity om = omMapping.get(e.getId());
                Set<Integer> pizzaIds = mMapping.get(e.getId());
                if (!CollectionUtils.isEmpty(pizzaIds)) {
                    List<Pizza> pizzas = menuEntities.stream()
                            .filter(m -> pizzaIds.contains(m.getId()))
                            .map(m -> EntityConverter.convert(om, m))
                            .collect(Collectors.toList());
                    order.setPizzas(pizzas);
                    result.add(order);
                }
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
        Optional<UserAddressBean> userAddressBean =
                userAddressJpaRepository.findUserAddressBeanByUserIdAndAddressId(entity.getUserId(), entity.getAddressId());
        userAddressBean.ifPresent(addressEntity -> {
            order.setAddress(EntityConverter.convert(addressEntity));
        });
    }

    /**
     * Query and supplement order menu info to {@param order}.
     *
     * @param entity order db entity.
     * @param order order to supplement.
     */
    private void supplementPizzas(OrderEntity entity, Order order, List<Pizza> menu) {
        List<OrderMenuEntity> orderMenuEntities = orderMenuJpaRepository.findByOrderId(entity.getId());
        if (!CollectionUtils.isEmpty(orderMenuEntities)) {

            List<Pizza> pizzas = new ArrayList<>();
            for (OrderMenuEntity om : orderMenuEntities) {
                menu.stream()
                        .filter(m -> Objects.equals(m.getId(), om.getMenuId()))
                        .findFirst()
                        .ifPresent(m -> pizzas.add(this.copy(m, om.getCount())));
            }
            order.setPizzas(pizzas);
        }
    }

    private Pizza copy(Pizza menu, int count) {
        Pizza pizza = new Pizza();
        BeanUtils.copyProperties(menu, pizza);
        pizza.setCount(count);
        return pizza;
    }

    /**
     * Query and supplement order menu info to {@param order}.
     *
     * @param entity order db entity.
     * @param order order to supplement.
     */
    private void supplementPizzas(OrderEntity entity, Order order, boolean onlyOnSale) {
        List<PizzaBean> menuEntities = null;
        if (onlyOnSale) {
            menuEntities = menuJpaRepository.findPizzaBeansByStateAndOrderId(
                    PizzaStatus.IN_SALE.getDbValue(),
                    entity.getId());
        } else {
            menuEntities = menuJpaRepository.findPizzaBeansByOrderId(
                    entity.getId());
        }
        List<Pizza> pizzas = new ArrayList<>();
        for (PizzaBean bean : menuEntities) {
            pizzas.add(EntityConverter.convert(bean));
        }
        order.setPizzas(pizzas);
    }

    

}
