package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Shop;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.AdminService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

public class ShopControllerTest extends TestApplication{
    private static final Logger logger = LoggerFactory.getLogger(OrderControllerTest.class);
    @Autowired
    ShopController shopController;
    @Autowired
    AdminService adminService;

    @Test
    public void testGetShopList() {
        List<Shop> shopList = shopController.getShopList();
        logger.info("Total shop number is {}",shopList.size());
        Assert.assertEquals(3,shopList.size());
    }

    @Test
    public void testIngredientListByShopId() {
        List<Ingredient> ingredientList = shopController.getIngredientListByShopId(1);
        logger.info("Total ingredient number is {}",ingredientList.size());
        Assert.assertEquals(2,ingredientList.size());
    }

    @Test
    public void testEditShopDetailAdminNotLogin()throws ParseException,BusinessServerException{
        ShopDetailRequest request = new ShopDetailRequest("1",200,"必胜客","普陀区","12345","","2019-03-15 09:52:41","2019-03-15 23:52:41",31.239072,121.418481);
        ShopDetailResponse response=shopController.editShopDetail(request);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testEditShopDetailSuccessfully()throws ParseException,BusinessServerException{
        adminService.adminLogin(new LoginRequest("admin","admin"));
        ShopDetailRequest request = new ShopDetailRequest("1",200,"必胜客","普陀区","12345","","2019-03-15 09:52:41","2019-03-15 23:52:41",31.239072,121.418481);
        ShopDetailResponse response=shopController.editShopDetail(request);
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testAddNewShopAdminNotLogin()throws ParseException,BusinessServerException{
        ShopDetailRequest request = new ShopDetailRequest(200,"必胜客","普陀区","12345","","2019-03-15 09:52:41","2019-03-15 23:52:41",31.239072,121.418481);
        ShopDetailResponse response=shopController.addNewShop(request);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);

    }

}
