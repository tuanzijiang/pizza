package edu.ecnu.scsse.pizza.bussiness.server;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.DriverStatus;
import edu.ecnu.scsse.pizza.data.enums.IngredientStatus;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyInt;

public class FakeFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(FakeFactory.class);

    private static final String ORDER_ID = "AAA";

    private static final Integer USER_ID = 1;

    private static final Integer PIZZA_ID = 1;

    public static MenuEntity fakeMenu(){
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setId(PIZZA_ID);
        menuEntity.setName("pizza"+PIZZA_ID);
        menuEntity.setState(PizzaStatus.IN_SALE.getDbValue());
        return menuEntity;
    }

    public static List<MenuEntity> fakeMenuEntities(){
        List<MenuEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MenuEntity menu = new MenuEntity();
            menu.setId(i);
            menu.setName("pizza"+i);
            list.add(menu);
        }
        return list;
    }

    public static IngredientEntity fakeIngredient(int id){
        IngredientEntity entity = new IngredientEntity();
        entity.setId(id);
        entity.setName("ingredient"+id);
        entity.setState(IngredientStatus.USING.getDbValue());
        entity.setAlermNum(200);
        entity.setCount(100);
        entity.setSupplierName("supplierName");
        return entity;
    }

    public static List<IngredientEntity> fakeIngredientEntities(){
        List<IngredientEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            IngredientEntity ingredient = new IngredientEntity();
            ingredient.setId(i);
            ingredient.setState(IngredientStatus.USING.getDbValue());
            ingredient.setName("ingredient"+i);
            list.add(ingredient);
        }
        return list;
    }

    public static List<Ingredient> fakeIngredientsById(List<Integer> ingredientIds){
        List<Ingredient> list = new ArrayList<>();
        for (int id:ingredientIds) {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(id);
            ingredient.setState(IngredientStatus.USING.getDbValue());
            ingredient.setName("ingredient"+id);
            list.add(ingredient);
        }
        return list;
    }

    public static Optional<MenuIngredientEntity> fakeMenuIngredientEntity(int menuId, int ingredientId){
        MenuIngredientEntity menuIngredientEntity = new MenuIngredientEntity();
        menuIngredientEntity.setId(menuId);
        menuIngredientEntity.setMenuId(menuId);
        menuIngredientEntity.setIngredientId(ingredientId);
        menuIngredientEntity.setCount(1);
        return Optional.of(menuIngredientEntity);
    }

    public static List<MenuIngredientEntity> fakeMenuIngredient(int menuId, List<Integer> ingredientIds){
        List<MenuIngredientEntity> menuIngredientEntityList = new ArrayList<>();
        for(int ingredientId:ingredientIds){
            MenuIngredientEntity menuIngredientEntity = new MenuIngredientEntity();
            menuIngredientEntity.setId(menuId);
            menuIngredientEntity.setMenuId(menuId);
            menuIngredientEntity.setIngredientId(ingredientId);
            menuIngredientEntity.setCount(1);
            menuIngredientEntityList.add(menuIngredientEntity);
        }
        return menuIngredientEntityList;
    }

    public static List<DriverEntity> fakeDriverEntities(){
        List<DriverEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DriverEntity driver = new DriverEntity();
            driver.setId(i);
            driver.setState(DriverStatus.LEISURE.getDbValue());
            driver.setName("driver"+i);
            list.add(driver);
        }
        return list;
    }

    public static DriverEntity fakeDriver(int driverId){
        DriverEntity entity = new DriverEntity();
        entity.setId(driverId);
        entity.setName("driver"+driverId);
        entity.setPhone("111");
        return entity;
    }

    public static PizzaShopEntity fakeShop(){
        PizzaShopEntity entity = new PizzaShopEntity();
        entity.setId(1);
        entity.setName("shop1");
        entity.setPhone("111");
        return entity;
    }

    public static List<PizzaShopEntity> fakeShops(){
        List<PizzaShopEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PizzaShopEntity shop = new PizzaShopEntity();
            shop.setId(i);
            shop.setPhone("12345");
            shop.setName("shop"+i);
            shop.setMaxNum(1000);
            shop.setLat(new BigDecimal(0));
            shop.setLon(new BigDecimal(0));
            shop.setStartTime(new Timestamp((new Date()).getTime()));
            shop.setEndTime(new Timestamp((new Date()).getTime()));
            list.add(shop);
        }
        return list;
    }

    public static List<ShopIngredientEntity> fakeShopIngredients(){
        List<ShopIngredientEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShopIngredientEntity entity = new ShopIngredientEntity();
            entity.setId(i);
            entity.setIngredientId(i);
            entity.setShopId(i);
            entity.setCount(100);
            list.add(entity);
        }
        return list;
    }

    public static List<ShopIngredientEntity> fakeShopIngredients(int shopId){
        List<ShopIngredientEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShopIngredientEntity entity = new ShopIngredientEntity();
            entity.setId(i);
            entity.setIngredientId(i);
            entity.setShopId(shopId);
            entity.setCount(100);
            list.add(entity);
        }
        return list;
    }

    public static ShopIngredientEntity fakeShopIngredient(int shopId,int ingredientId){
        ShopIngredientEntity entity = new ShopIngredientEntity();
        entity.setId(1);
        entity.setIngredientId(ingredientId);
        entity.setShopId(shopId);
        entity.setCount(100);
        return entity;
    }

    public static OrderEntity fakeOrder(String orderUuid){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderUuid(orderUuid);
        orderEntity.setCommitTime(new Timestamp((new Date()).getTime()));
        return orderEntity;
    }

    public static OrderEntity fakeCheckingOrder(int orderId){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setState(OrderStatus.CANCEL_CHECKING.getDbValue());
        orderEntity.setId(orderId);
        orderEntity.setCommitTime(new Timestamp((new Date()).getTime()));
        return orderEntity;
    }

    public static OrderEntity fakeNotCheckingOrder(int orderId){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setState(OrderStatus.UNKNOWN.getDbValue());
        orderEntity.setId(orderId);
        orderEntity.setCommitTime(new Timestamp((new Date()).getTime()));
        return orderEntity;
    }

    public static List<OrderEntity> fakeOrders(){
        List<OrderEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderEntity entity = new OrderEntity();
            entity.setId(i);
            entity.setShopId(i);
            entity.setState(OrderStatus.UNKNOWN.getDbValue());
            entity.setUserId(i);
            entity.setTotalPrice(100.0);
            entity.setCommitTime(new Timestamp((new Date()).getTime()));
            entity.setDeliverStartTime(new Timestamp((new Date()).getTime()));
            entity.setDeliverEndTime(new Timestamp((new Date()).getTime()));
            list.add(entity);
        }
        return list;
    }

    public static List<OrderEntity> fakePendingOrders(){
        List<OrderEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderEntity entity = new OrderEntity();
            entity.setId(i);
            entity.setShopId(i);
            entity.setState(OrderStatus.CANCEL_CHECKING.getDbValue());
            entity.setUserId(i);
            entity.setTotalPrice(100.0);
            entity.setCommitTime(new Timestamp((new Date()).getTime()));
            entity.setDeliverStartTime(new Timestamp((new Date()).getTime()));
            entity.setDeliverEndTime(new Timestamp((new Date()).getTime()));
            list.add(entity);
        }
        return list;
    }

    public static List<OrderEntity> fakeCancelOrderList(){
        List<OrderEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderEntity entity = new OrderEntity();
            entity.setId(i);
            entity.setShopId(i);
            entity.setState(OrderStatus.CANCELED.getDbValue());
            entity.setUserId(i);
            entity.setTotalPrice(100.0);
            entity.setCommitTime(new Timestamp((new Date()).getTime()));
            entity.setDeliverStartTime(new Timestamp((new Date()).getTime()));
            entity.setDeliverEndTime(new Timestamp((new Date()).getTime()));
            list.add(entity);
        }
        return list;
    }

    public static List<OrderEntity> fakeOrdersByOneDate(String date) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date d = sdf.parse(date);
        List<OrderEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderEntity entity = new OrderEntity();
            entity.setId(i);
            entity.setShopId(i);
            entity.setState(OrderStatus.UNKNOWN.getDbValue());
            entity.setUserId(i);
            entity.setTotalPrice(100.0);
            entity.setCommitTime(new Timestamp(d.getTime()));
            entity.setDeliverStartTime(new Timestamp((new Date()).getTime()));
            entity.setDeliverEndTime(new Timestamp((new Date()).getTime()));
            list.add(entity);
        }
        return list;
    }

    public static List<OrderMenuEntity> fakeOrderMenuEntities(int orderId){
        List<OrderMenuEntity> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
            orderMenuEntity.setId(i);
            orderMenuEntity.setMenuId(i);
            orderMenuEntity.setOrderId(orderId);
            orderMenuEntity.setCount(5);
            list.add(orderMenuEntity);
        }
        return list;
    }

    public static List<MenuIngredientEntity> fakeMenuIngredientEntities(int menuId){
        List<MenuIngredientEntity> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            MenuIngredientEntity menuIngredientEntity = new MenuIngredientEntity();
            menuIngredientEntity.setId(i);
            menuIngredientEntity.setMenuId(menuId);
            menuIngredientEntity.setIngredientId(i);
            menuIngredientEntity.setCount(5);
            list.add(menuIngredientEntity);
        }
        return list;
    }

    public static AddressEntity fakeAddressEntity(int addressId){
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(addressId);
        addressEntity.setAddress("address");
        addressEntity.setLat(new BigDecimal(0));
        addressEntity.setLon(new BigDecimal(0));
        return addressEntity;
    }
}
