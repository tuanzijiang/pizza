package edu.ecnu.scsse.pizza.consumer.server;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.exception.ExceptionType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Pizza;
import edu.ecnu.scsse.pizza.consumer.server.service.OrderService;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeFactory.class);

    private static final String ORDER_ID = "AAA";

    private static final Integer USER_ID = 1;

    private static final Integer PIZZA_ID = 1;

    public static List<Pizza> fakePizzas() {
        List<Pizza> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Pizza pizza = new Pizza();
            pizza.setId(i);
            pizza.setName("pizza" + i);
            pizza.setState(PizzaStatus.IN_SALE);
            pizza.setPrice(10.1);
            list.add(pizza);
        }
        return list;
    }

    public static Order fakeCartOrder() {
        Order order = new Order();

        List<Pizza> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Pizza pizza = new Pizza();
            pizza.setId(i);
            pizza.setName("pizza" + i);
            pizza.setState(PizzaStatus.IN_SALE);
            pizza.setCount(1);
            pizza.setPrice(10.1);
        }
        order.setId(ORDER_ID);
        order.setPizzas(list);
        order.setStatus(OrderStatus.CART);
        return order;
    }



    public static Optional<OrderEntity> fakeCartOrderEntity(Integer orderId, String orderUuid, Integer userId) {
        OrderEntity order = new OrderEntity();
        order.setOrderUuid(orderUuid);
        order.setId(orderId);
        order.setUserId(userId);
        order.setState(OrderStatus.CART.getDbValue());
        return Optional.of(order);
    }

    public static Order fakeOrder() {
        Order order = new Order();
        order.setId(ORDER_ID);
        return order;
    }

    public static Order fakeOrder(OrderStatus status) {
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setStatus(status);
        return order;
    }

    public static Optional<OrderEntity> fakeOrderEntity(Integer orderId, String orderUuid, Integer userId, Integer addressId) {
        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setOrderUuid(orderUuid);
        order.setUserId(userId);
        order.setAddressId(addressId);
        order.setState(OrderStatus.FINISH.getDbValue());
        return Optional.of(order);
    }

    public static Optional<OrderEntity> fakeOrderEntity(Integer orderId, String orderUuid, Integer userId, Integer addressId, OrderStatus status) {
        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setOrderUuid(orderUuid);
        order.setUserId(userId);
        order.setAddressId(addressId);
        order.setState(OrderStatus.FINISH.getDbValue());
        order.setState(status.getDbValue());
        return Optional.of(order);
    }

    public static Optional<UserAddressEntity> fakeUserAddressEntity(Integer userId, Integer addressId) {
        UserAddressEntity entity = new UserAddressEntity();
        entity.setId(addressId);
        entity.setUserId(userId);
        entity.setAddressId(addressId);
        return Optional.of(entity);
    }

    public static Optional<AddressEntity> fakeAddressEntity(Integer addressId) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(addressId);
        addressEntity.setAddress("ADDRESS");
        addressEntity.setLat(new BigDecimal(1));
        addressEntity.setLon(new BigDecimal(1));
        return Optional.of(addressEntity);
    }

    public static List<OrderMenuEntity> fakeOrderMenuEntities(Integer orderId, List<Integer> menuIds) {
        List<OrderMenuEntity> entities = new ArrayList<>();
        menuIds.stream().forEach(i -> {
            OrderMenuEntity e = new OrderMenuEntity();
            e.setOrderId(orderId);
            e.setMenuId(i);
            e.setCount(1);
            entities.add(e);
        });
        return entities;
    }

    public static List<OrderMenuEntity> fakeOrderMenuEntities(List<Integer> orderId) {
        List<OrderMenuEntity> entities = new ArrayList<>();
        orderId.stream().forEach(i -> {
            OrderMenuEntity e = new OrderMenuEntity();
            e.setOrderId(i);
            e.setMenuId(i);
            e.setCount(1);
            entities.add(e);
        });
        return entities;
    }

    public static List<MenuEntity> fakeMenuEntities(List<Integer> menuIds) {
        List<MenuEntity> entities = new ArrayList<>();
        menuIds.stream().forEach(i -> {
            MenuEntity e = new MenuEntity();
            e.setId(i);
            e.setName("pizza" + i);
            entities.add(e);
        });
        return entities;
    }


    public static List<Order> fakeOrders() {
        List<Order> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setId(String.valueOf(i));
            list.add(order);
        }
        return list;
    }

    public static List<OrderEntity> fakeOrderEntities() {
        List<OrderEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(i);
            order.setUserId(USER_ID);
            order.setOrderUuid(String.valueOf(i));
            list.add(order);
        }
        return list;
    }

    public static OrderService.Phones fakePhones() {
        OrderService.Phones phones = new OrderService.Phones();
        phones.setShopPhone("111");
        phones.setDeliverymanPhone("222");
        phones.setServicePhone("333");
        return phones;
    }

    public static ConsumerServerException fakeException() {
        return new ConsumerServerException(ExceptionType.CONTROLLER,
                "Fake Exception.", new IllegalStateException());
    }

    public static Optional<DriverEntity> fakeDriver(Integer driverId) {
        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setId(driverId);
        driverEntity.setPhone("11111");
        return Optional.of(driverEntity);
    }

    public static Optional<PizzaShopEntity> fakeShop(Integer shopId) {
        PizzaShopEntity shopEntity = new PizzaShopEntity();
        shopEntity.setId(shopId);
        shopEntity.setPhone("22222");
        return Optional.of(shopEntity);
    }
}
