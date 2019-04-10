package edu.ecnu.scsse.pizza.consumer.server.service;


import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.FakeFactory;
import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Pizza;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import edu.ecnu.scsse.pizza.data.repository.*;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static edu.ecnu.scsse.pizza.consumer.server.utils.ThrowableCaptor.thrownBy;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderJpaRepository orderJpaRepository;

    @Mock
    private UserAddressJpaRepository userAddressJpaRepository;

    @Mock
    private AddressJpaRepository addressJpaRepository;

    @Mock
    private OrderMenuJpaRepository orderMenuJpaRepository;

    @Mock
    private MenuJpaRepository menuJpaRepository;

    @Mock
    private DriverJpaRepository driverJpaRepository;

    @Mock
    private PizzaShopJpaRepository pizzaShopJpaRepository;

    @InjectMocks
    private OrderService orderService = new OrderService();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
//
//    @Test
//    public void testPaid() {
//        double price = 20.12;
//        String orderUuid = "AAA";
//        when(orderJpaRepository.updateStateAndTotalPriceByOrderUuid(OrderStatus.PAID.getDbValue(), price, orderUuid))
//                .thenReturn(1);
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        Map<String, String[]> map = new HashMap<>();
//        map.put("out_trade_no", new String[]{orderUuid});
//        map.put("total_amount", new String[]{String.valueOf(price)});
//        when(request.getParameterMap()).thenReturn(map);
//        when(request.getParameter("out_trade_no")).thenReturn(orderUuid);
//        when(request.getParameter("total_amount")).thenReturn(String.valueOf(price));
//
//        boolean result = orderService.paid(request);
//        assertTrue(result);
//        verify(orderJpaRepository).updateStateAndTotalPriceByOrderUuid(OrderStatus.PAID.getDbValue(), price, orderUuid);
//    }
//
//    @Test
//    public void testPaidFail() {
//        double price = 20.12;
//        String orderUuid = "AAA";
//        when(orderJpaRepository.updateStateAndTotalPriceByOrderUuid(OrderStatus.PAID.getDbValue(), price, orderUuid))
//                .thenReturn(0);
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getParameter("out_trade_no")).thenReturn(orderUuid);
//        when(request.getParameter("total_amount")).thenReturn(String.valueOf(price));
//
//        boolean result = orderService.paid(request);
//        assertFalse(result);
//        verify(orderJpaRepository).updateStateAndTotalPriceByOrderUuid(OrderStatus.PAID.getDbValue(), price, orderUuid);
//    }


    @Test
    public void testFetchCartOrder() throws ConsumerServerException {
        Integer orderId = 1;
        String orderUuid = "AAA";
        Integer userId = 1;
        List<Integer> menuIds = Lists.newArrayList(1,2,3);

        Optional<OrderEntity> orderEntity = FakeFactory.fakeCartOrderEntity(orderId, orderUuid, userId);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(orderEntity);

        when(orderMenuJpaRepository.findByOrderId(orderId))
                .thenReturn(FakeFactory.fakeOrderMenuEntities(orderId, menuIds));
        when(menuJpaRepository.findAllByStateAndIdIn(
                anyInt(), anyList())).thenReturn(FakeFactory.fakeMenuEntities(menuIds));

        Order order = orderService.fetchOrder(orderUuid);
        assertEquals(order.getId(), orderUuid);
        assertNull(order.getAddress());
        // assertEquals(order.getPizzas().size(), menuIds.size());
        assertEquals(order.getStatus(), OrderStatus.CART);
    }

    @Test
    public void testFetchUnknownOrder() {
        String orderUuid = "AAA";
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(Optional.empty());

        NotFoundException e = (NotFoundException) thrownBy(() -> orderService.fetchOrder(orderUuid));
        assertEquals(e.getMessage(), String.format("Order with order id %s is not found.", orderUuid));
    }

    @Test
    public void testFetchOrder() throws ConsumerServerException {
        Integer orderId = 1;
        String orderUuid = "AAA";
        Integer userId = 1;
        Integer addressId = 1;
        List<Integer> menuIds = Lists.newArrayList(1,2,3);
        Optional<OrderEntity> orderEntity = FakeFactory.fakeOrderEntity(orderId, orderUuid, userId, addressId);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(orderEntity);

        when(userAddressJpaRepository.findByUserIdAndAddressId(userId, addressId))
                .thenReturn(FakeFactory.fakeUserAddressEntity(userId, addressId));
        when(addressJpaRepository.findById(addressId))
                .thenReturn(FakeFactory.fakeAddressEntity(addressId));
        when(orderMenuJpaRepository.findByOrderId(orderId))
                .thenReturn(FakeFactory.fakeOrderMenuEntities(orderId, menuIds));
        when(menuJpaRepository.findAllById(anyList()))
                .thenReturn(FakeFactory.fakeMenuEntities(menuIds));

        Order order = orderService.fetchOrder(orderUuid);
        assertEquals(order.getId(), orderUuid);
        assertEquals(order.getAddress().getId(), addressId);
        // assertEquals(order.getPizzas().size(), menuIds.size());
        assertNotEquals(order.getStatus(), OrderStatus.CART);
    }

    @Test
    public void testFetchOrderWithUnknownUserAddress() throws ConsumerServerException {
        Integer orderId = 1;
        String orderUuid = "AAA";
        Integer userId = 1;
        Integer addressId = 1;
        Optional<OrderEntity> orderEntity = FakeFactory.fakeOrderEntity(orderId, orderUuid, userId, addressId);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(orderEntity);

        when(userAddressJpaRepository.findByUserIdAndAddressId(userId, addressId))
                .thenReturn(Optional.empty());

        Order order = orderService.fetchOrder(orderUuid);
        assertNull(order.getAddress());
    }

    @Test
    public void testFetchOrderWithUnknownAddress() throws ConsumerServerException {
        Integer orderId = 1;
        String orderUuid = "AAA";
        Integer userId = 1;
        Integer addressId = 1;
        Optional<OrderEntity> orderEntity = FakeFactory.fakeOrderEntity(orderId, orderUuid, userId, addressId);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(orderEntity);

        when(userAddressJpaRepository.findByUserIdAndAddressId(userId, addressId))
                .thenReturn(FakeFactory.fakeUserAddressEntity(userId, addressId));
        when(addressJpaRepository.findById(addressId))
                .thenReturn(Optional.empty());

        Order order = orderService.fetchOrder(orderUuid);
        assertNull(order.getAddress());
    }

    @Test
    public void testFetchOrderEmptyOrderMenu() throws ConsumerServerException {
        Integer orderId = 1;
        String orderUuid = "AAA";
        Integer userId = 1;
        Integer addressId = 1;

        Optional<OrderEntity> orderEntity = FakeFactory.fakeOrderEntity(orderId, orderUuid, userId, addressId);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(orderEntity);

        when(userAddressJpaRepository.findByUserIdAndAddressId(userId, addressId))
                .thenReturn(FakeFactory.fakeUserAddressEntity(userId, addressId));
        when(addressJpaRepository.findById(addressId))
                .thenReturn(FakeFactory.fakeAddressEntity(addressId));
        when(orderMenuJpaRepository.findByOrderId(orderId))
                .thenReturn(Collections.EMPTY_LIST);

        Order order = orderService.fetchOrder(orderUuid);
        assertEquals(order.getPizzas().size(), 0);
    }

    @Test
    public void testFetchOrderWithEmptyMenu() throws ConsumerServerException {
        Integer orderId = 1;
        String orderUuid = "AAA";
        Integer userId = 1;
        Integer addressId = 1;
        List<Integer> menuIds = Lists.newArrayList(1,2,3);
        Optional<OrderEntity> orderEntity = FakeFactory.fakeOrderEntity(orderId, orderUuid, userId, addressId);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(orderEntity);

        when(userAddressJpaRepository.findByUserIdAndAddressId(userId, addressId))
                .thenReturn(FakeFactory.fakeUserAddressEntity(userId, addressId));
        when(addressJpaRepository.findById(addressId))
                .thenReturn(FakeFactory.fakeAddressEntity(addressId));
        when(orderMenuJpaRepository.findByOrderId(orderId))
                .thenReturn(FakeFactory.fakeOrderMenuEntities(orderId, menuIds));
        when(menuJpaRepository.findAllById(anyList()))
                .thenReturn(Collections.EMPTY_LIST);

        Order order = orderService.fetchOrder(orderUuid);
        assertEquals(order.getPizzas().size(), 0);
    }

    @Test
    public void testFetchOrderFailure() {
        Integer orderId = 1;
        String orderUuid = "AAA";
        Integer userId = 1;
        Integer addressId = 1;
        Optional<OrderEntity> orderEntity = FakeFactory.fakeOrderEntity(orderId, orderUuid, userId, addressId);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(orderEntity);

        ConsumerServerException e = (ConsumerServerException) thrownBy(() -> orderService.fetchOrder(orderUuid));
        // assertEquals(e.getMessage(), "Fail to query Address while assembling the order entity.");
    }

    @Test
    public void testFetchOrdersWithNullUserId() {
        List<OrderStatus> orderStatuses = all();
        int count = 20;
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.fetchOrders(null, orderStatuses, "", count));

        assertEquals(e.getMessage(), "userId can't be null.");
    }

    @Test
    public void testFetchOrdersWithEmptyStatus() {
        List<OrderStatus> orderStatuses = Collections.EMPTY_LIST;
        int count = 20;
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.fetchOrders(1, orderStatuses, "", count));

        assertEquals(e.getMessage(), "orderStatuses can't be empty.");
    }

    @Test
    public void testFetchOrdersWithNegativeCount() {
        List<OrderStatus> orderStatuses = all();
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.fetchOrders(1, orderStatuses, "", -1));

        assertEquals(e.getMessage(), "count must be positive.");
    }

    @Test
    public void testFetchOrdersWithZeroCount() {
        List<OrderStatus> orderStatuses = all();
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.fetchOrders(1, orderStatuses, "", 0));

        assertEquals(e.getMessage(), "count must be positive.");
    }

    @Test
    public void testFetchOrders() throws ConsumerServerException {
        List<OrderStatus> orderStatuses = all();
        int count = 20;
        List<OrderEntity> entities = FakeFactory.fakeOrderEntities();
        List<Integer> ids = entities.stream().mapToInt(e -> e.getId()).boxed().collect(Collectors.toList());
        when(orderJpaRepository.findByUserIdAndStateInAndIdGreaterThan(
                anyInt(), anyList(), anyInt(), any())).thenReturn(entities);
        when(orderMenuJpaRepository.findByOrderIdIn(anyList())).thenReturn(
                FakeFactory.fakeOrderMenuEntities(ids));
        when(menuJpaRepository.findAllById(anyList())).thenReturn(FakeFactory.fakeMenuEntities(ids));

        List<Order> orders = orderService.fetchOrders(1, orderStatuses, "", count);

        assertEquals(entities.size(), orders.size());
    }

    @Test
    public void testFetchOrdersWithEmptyResult() throws ConsumerServerException {
        List<OrderStatus> orderStatuses = all();
        int count = 20;
        when(orderJpaRepository.findByUserIdAndStateInAndIdGreaterThan(
                anyInt(), anyList(), anyInt(), any())).thenReturn(Collections.EMPTY_LIST);

        List<Order> orders = orderService.fetchOrders(1, orderStatuses, "", count);

        assertEquals(0, orders.size());
    }

    @Test
    public void testFetchOrdersWithUnknownLast() {
        List<OrderStatus> orderStatuses = all();
        String lastUuid = "last";
        int count = 20;
        when(orderJpaRepository.findIdByOrderUuid(lastUuid)).thenReturn(Optional.empty());

        NotFoundException e = (NotFoundException) thrownBy(() ->
                orderService.fetchOrders(1, orderStatuses, lastUuid, count));

        assertEquals(e.getMessage(), String.format("There is no order where 'orderUuid'={%s}", lastUuid));
    }

    private List<OrderStatus> all() {
        return Lists.newArrayList(OrderStatus.values());
    }

    @Test
    public void testGetCartOrderWithNullUserId() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.getCartOrder(null, Collections.EMPTY_LIST));
        assertEquals(e.getMessage(), "userId must not be NULL.");
    }

    @Test
    public void testGetCartOrder() throws ConsumerServerException {

        Integer userId = 1;
        List<Pizza> pizzas = FakeFactory.fakePizzas();

        Optional<OrderEntity> orderEntity = FakeFactory.fakeCartOrderEntity(1, "AAA", userId);
        when(orderJpaRepository.findFirstByUserIdAndStateOrderByIdDesc(userId, OrderStatus.CART.getDbValue()))
                .thenReturn(orderEntity);
        when(orderMenuJpaRepository.findByOrderId(1))
                .thenReturn(FakeFactory.fakeOrderMenuEntities(1, Lists.newArrayList(1,2,3)));
        Order order = orderService.getCartOrder(userId, pizzas);
        assertEquals(order.getId(), "AAA");
        assertEquals(order.getStatus(), OrderStatus.CART);
        verify(orderJpaRepository, never()).save(any());
    }

    @Test
    public void testGetCartOrderNew() throws ConsumerServerException {

        Integer userId = 1;
        List<Pizza> pizzas = FakeFactory.fakePizzas();

        when(orderJpaRepository.findFirstByUserIdAndStateOrderByIdDesc(userId, OrderStatus.CART.getDbValue()))
                .thenReturn(Optional.empty());
        Order order = orderService.getCartOrder(userId, pizzas);

        assertEquals(order.getStatus(), OrderStatus.CART);
        verify(orderJpaRepository).save(any());
    }

    @Test
    public void testGetInSaleMenu() {
        List<MenuEntity> entities = FakeFactory.fakeMenuEntities(Lists.newArrayList(1,2,3));
        when(menuJpaRepository.findAllByState(PizzaStatus.IN_SALE.getDbValue())).thenReturn(entities);
        assertEquals(orderService.getInSaleMenu().size(), entities.size());
    }

    @Test
    public void testUpdateOrderWithNullOrderId() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.updateOrder(null, 1, 1));
        assertNotNull(e);
    }

    @Test
    public void testUpdateOrderWithZeroMenuId() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.updateOrder("AAA", 0, 1));
        assertNotNull(e);
    }

    @Test
    public void testUpdateOrderWithNegativeMenuId() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.updateOrder("AAA", -1, 1));
        assertNotNull(e);
    }

    @Test
    public void testUpdateOrderWithZeroCount() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.updateOrder("AAA", 1, 0));
        assertNotNull(e);
    }

    @Test
    public void testUpdateOrderWithNegativeCount() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.updateOrder("AAA", 1, -1));
        assertNotNull(e);
    }

    @Test
    public void testUpdateOrder() throws ConsumerServerException {
        String orderUuid = "AAA";
        Integer menuId = 1;
        Integer count = 1;

        when(orderJpaRepository.findCartIdByOrderUuid(orderUuid)).thenReturn(Optional.of(1));
        when(orderMenuJpaRepository.updateCount(count, 1, menuId)).thenReturn(1);
        assertEquals(1, orderService.updateOrder(orderUuid, menuId, count));
    }

    @Test
    public void testUpdateOrderWithUnknownCart() {
        String orderUuid = "AAA";
        Integer menuId = 1;
        Integer count = 1;

        when(orderJpaRepository.findCartIdByOrderUuid(orderUuid)).thenReturn(Optional.empty());
        NotFoundException e = (NotFoundException) thrownBy(() -> orderService.updateOrder(orderUuid, menuId, count));
        assertEquals(e.getMessage(), String.format("Fail to find cart order with orderUuid=[%s]", orderUuid));
    }

    @Test
    public void testCancelOrderWithUnknownOrder() {
        String orderUuid = "1";
        when(orderJpaRepository.findByOrderUuid(anyString())).thenReturn(Optional.empty());
        NotFoundException e = (NotFoundException) thrownBy(() -> orderService.cancelOrder(orderUuid));
        assertEquals(e.getMessage(), String.format("No order found with orderUuid[%s]", orderUuid));

    }

    @Test
    public void cancelOrder() throws ConsumerServerException {
        String orderUuid = "1";
        OrderEntity orderEntity = FakeFactory.fakeOrderEntity(1, orderUuid, 1, 1).get();
        orderEntity.setCommitTime(new Timestamp(System.currentTimeMillis()));
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(Optional.of(orderEntity));
        assertTrue(orderService.cancelOrder(orderUuid));
    }

    @Test
    public void testCancelOrderFailureSinceOverTime() throws ConsumerServerException {
        String orderUuid = "1";
        OrderEntity orderEntity = FakeFactory.fakeOrderEntity(1, orderUuid, 1, 1).get();
        orderEntity.setCommitTime(new Timestamp(System.currentTimeMillis() - 20 * 60 * 1000));
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(Optional.of(orderEntity));
        assertFalse(orderService.cancelOrder(orderUuid));
    }

    @Test
    public void testCancelOrderBeforeCommit() throws ConsumerServerException {
        String orderUuid = "1";
        OrderEntity orderEntity = FakeFactory.fakeOrderEntity(1, orderUuid, 1, 1).get();
        orderEntity.setCommitTime(null);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(Optional.of(orderEntity));
        assertFalse(orderService.cancelOrder(orderUuid));
    }

    @Test
    public void testGetPhones() throws ConsumerServerException {
        String orderUuid = "AAA";

        Optional<OrderEntity> order = FakeFactory.fakeOrderEntity(1, orderUuid, 1, 1);
        order.get().setDriverId(1);
        order.get().setShopId(1);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(order);

        Optional<DriverEntity> driverEntity = FakeFactory.fakeDriver(1);
        when(driverJpaRepository.findById(1)).thenReturn(driverEntity);;

        Optional<PizzaShopEntity> shopEntity = FakeFactory.fakeShop(1);
        when(pizzaShopJpaRepository.findById(1)).thenReturn(shopEntity);
        OrderService.Phones phones = orderService.getPhones(orderUuid);

        assertEquals(shopEntity.get().getPhone(), phones.getShopPhone());
        assertEquals(driverEntity.get().getPhone(), phones.getDeliverymanPhone());
        assertEquals("021-9999-9999", phones.getServicePhone());
    }

    @Test
    public void testGetPhonesWithUnknownOrder() {
        when(orderJpaRepository.findByOrderUuid(anyString())).thenReturn(Optional.empty());

        NotFoundException e = (NotFoundException) thrownBy(() -> orderService.getPhones("aaa"));
        assertEquals("No order found with orderUuid[aaa]", e.getMessage());
    }

    @Test
    public void testGetPhonesWithUnknownDriver() {
        String orderUuid = "AAA";

        Optional<OrderEntity> order = FakeFactory.fakeOrderEntity(1, orderUuid, 1, 1);
        order.get().setDriverId(1);
        order.get().setShopId(1);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(order);

        when(driverJpaRepository.findById(1)).thenReturn(Optional.empty());
        NotFoundException e = (NotFoundException) thrownBy(() -> orderService.getPhones(orderUuid));
        assertEquals("No driver found with id[1]", e.getMessage());
    }

    @Test
    public void testGetPhonesWithUnknownShop() {
        String orderUuid = "AAA";

        Optional<OrderEntity> order = FakeFactory.fakeOrderEntity(1, orderUuid, 1, 1);
        order.get().setDriverId(1);
        order.get().setShopId(1);
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(order);

        Optional<DriverEntity> driverEntity = FakeFactory.fakeDriver(1);
        when(driverJpaRepository.findById(1)).thenReturn(driverEntity);

        when(pizzaShopJpaRepository.findById(anyInt())).thenReturn(Optional.empty());
        NotFoundException e = (NotFoundException) thrownBy(() -> orderService.getPhones(orderUuid));
        assertEquals("No shop found with id[1]", e.getMessage());
    }

    @Test
    public void sendOrder() {
    }

}