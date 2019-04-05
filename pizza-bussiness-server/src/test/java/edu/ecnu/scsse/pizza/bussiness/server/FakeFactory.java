package edu.ecnu.scsse.pizza.bussiness.server;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.DriverStatus;
import edu.ecnu.scsse.pizza.data.enums.IngredientStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    public static Optional<PizzaShopEntity> fakeShop(){
        PizzaShopEntity entity = new PizzaShopEntity();
        entity.setId(1);
        entity.setName("shop1");
        entity.setPhone("111");
        return Optional.of(entity);
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

    public static ShopIngredientEntity fakeShopIngredient(int shopId,int ingredientId){
        ShopIngredientEntity entity = new ShopIngredientEntity();
        entity.setId(1);
        entity.setIngredientId(ingredientId);
        entity.setShopId(shopId);
        entity.setCount(100);
        return entity;
    }


}
