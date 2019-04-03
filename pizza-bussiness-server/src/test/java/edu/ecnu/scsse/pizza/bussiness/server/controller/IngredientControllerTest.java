package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.ShopIngredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.BatchImportResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.BuyIngredientRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.AdminService;
import edu.ecnu.scsse.pizza.data.enums.IngredientStatus;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IngredientControllerTest  extends TestApplication{
    private static final Logger logger = LoggerFactory.getLogger(IngredientControllerTest.class);
    @Autowired
    IngredientController ingredientController;
    @Autowired
    AdminService adminService;

    @Test
    public void testGetIngredientList(){
        List<IngredientDetailResponse> ingredientList=ingredientController.getIngredientList();
        logger.info("Total ingredient number is {}",ingredientList.size());
        Assert.assertEquals(6,ingredientList.size());
    }

//    @Test
//    public void testBatchImportByExcelFile(){
//        String excelPath = "/Users/helinzi/Downloads/ingredient.xlsx";
//        BatchImportResponse response=ingredientController.batchImportByExcelFile(excelPath);
//        Assert.assertEquals(ResultType.FAILURE,response.getResultType());
//    }

    @Test
    public void testEditIngredientDetailAdminNotLogin() throws BusinessServerException{
        IngredientDetailRequest request = new IngredientDetailRequest(1,"榴莲",1000,400,"水果商", IngredientStatus.USING);
        SimpleResponse response=ingredientController.editIngredientDetail(request);
        Assert.assertEquals(ResultType.FAILURE,response.getResultType());
    }

    @Test
    public void testEditIngredientDetailSuccessfully() throws BusinessServerException{
        adminService.adminLogin(new LoginRequest("admin","admin"));
        IngredientDetailRequest request = new IngredientDetailRequest(1,"榴莲",1000,400,"水果商", IngredientStatus.USING);
        SimpleResponse response=ingredientController.editIngredientDetail(request);
        Assert.assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testAddNewIngredientAdminNotLogin() throws BusinessServerException{
        IngredientDetailRequest request = new IngredientDetailRequest("榴莲",1000,400,"水果商", IngredientStatus.USING);
        SimpleResponse response=ingredientController.addNewIngredient(request);
        Assert.assertEquals(ResultType.FAILURE,response.getResultType());
    }


    @Test
    public void testEditIngredientStatusAdminNotLogin() throws BusinessServerException{
        SimpleResponse response=ingredientController.editIngredientStatus(1);
        Assert.assertEquals(ResultType.FAILURE,response.getResultType());
    }

    @Test
    public void testEditIngredientStatusSuccessfullyn() throws BusinessServerException{
        adminService.adminLogin(new LoginRequest("admin","admin"));
        SimpleResponse response=ingredientController.editIngredientStatus(1);
        Assert.assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testGetAlarmList() {
        List<ShopIngredient> ingredientList=ingredientController.getAlarmList();
        logger.info("Total ingredient number is {}",ingredientList.size());
        Assert.assertEquals(0,ingredientList.size());
    }

//    @Test
//    public void testBuyIngredientSuccessfully(){
//        adminService.adminLogin(new LoginRequest("admin","admin"));
//        BuyIngredientRequest request= new BuyIngredientRequest(1,1,300);
//        BaseResponse response = ingredientController.buyIngredient(request);
//        Assert.assertEquals(ResultType.SUCCESS,response.getResultType());
//    }
}
