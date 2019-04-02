package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.AdminService;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaTag;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MenuControllerTest extends TestApplication{
    private static final Logger logger = LoggerFactory.getLogger(OrderControllerTest.class);
    @Autowired
    MenuController menuController;
    @Autowired
    AdminService adminService;

    @Test
    public void testGetMenuList(){
        List<MenuDetailResponse> menuList=menuController.getMenuList();
        logger.info("Total menu number is {}",menuList.size());
        Assert.assertEquals(menuList.size(),4);
    }

    @Test
    public void testEditMenuStatusAdminNotLogin(){
        BaseResponse baseResponse=menuController.editMenuStatus(1);
        Assert.assertEquals(baseResponse.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testEditMenuStatusSuccessfully(){
        adminService.adminLogin(new LoginRequest("admin","admin"));
        BaseResponse baseResponse=menuController.editMenuStatus(1);
        Assert.assertEquals(baseResponse.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testEditMenuStatusFullAdminNotLogin()throws BusinessServerException {
        MenuDetailRequest request=new MenuDetailRequest("1","榴莲披萨",null,"",null,40, PizzaStatus.IN_SALE, PizzaTag.UNKNOWN);
        SimpleResponse response=menuController.editMenuStatus(request);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testEditMenuStatusFullSuccessfully()throws BusinessServerException {
        adminService.adminLogin(new LoginRequest("admin","admin"));
        List<Ingredient> ingredients=new ArrayList<>();
        ingredients.add(new Ingredient(1,"榴莲","水果商",0,400,700));
        MenuDetailRequest request=new MenuDetailRequest("1","榴莲披萨",null,"",ingredients,40, PizzaStatus.IN_SALE, PizzaTag.UNKNOWN);
        SimpleResponse response=menuController.editMenuStatus(request);
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testAddNewMenuAdminNotLogin()throws ParseException,BusinessServerException{
        MenuDetailRequest request=new MenuDetailRequest("榴莲披萨",null,"",null,40, PizzaStatus.IN_SALE, PizzaTag.UNKNOWN);
        BaseResponse response=menuController.addNewMenu(request);
        Assert.assertEquals(response.getResultType(),ResultType.FAILURE);
    }

//    @Test
//    public void testAddNewMenuSuccessfully()throws ParseException,BusinessServerException{
//        List<Ingredient> ingredients=new ArrayList<>();
//        ingredients.add(new Ingredient(1,"榴莲","水果商",0,400,700));
//        adminService.adminLogin(new LoginRequest("admin","admin"));
//        MenuDetailRequest request=new MenuDetailRequest("榴莲披萨",null,"",ingredients,40, PizzaStatus.IN_SALE, PizzaTag.UNKNOWN);
//        BaseResponse response=menuController.addNewMenu(request);
//        Assert.assertEquals(response.getResultType(),ResultType.SUCCESS);
//    }
}
