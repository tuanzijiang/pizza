package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.AdminServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AdminControllerTest extends TestApplication{
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceTest.class);
    @Autowired
    AdminController adminController;

    @Test
    public void testAdminLoginSuccessfully() {
        LoginRequest successRequest = new LoginRequest("admin","admin");
        LoginResponse response = adminController.adminLogin(successRequest);
        Assert.assertEquals(response.getAdminId(),2);
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testAdminLoginWrongPwd() {
        LoginRequest wrongPwdRequest = new LoginRequest("admin","123");
        LoginResponse response = adminController.adminLogin(wrongPwdRequest);
        Assert.assertEquals(response.getAdminId(),-1);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testAdminLoginWrongAdminName() {
        LoginRequest wrongAdminNameRequest = new LoginRequest("123","admin");
        LoginResponse response = adminController.adminLogin(wrongAdminNameRequest);
        Assert.assertEquals(response.getAdminId(),-1);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

//    @Test
//    public void testGetAdminLoggerList(){
//        List<edu.ecnu.scsse.pizza.bussiness.server.model.entity.Logger> adminLoggerList=adminController.getOperateLogger();
//        logger.info("Total order number is {}",adminLoggerList.size());
//        Assert.assertEquals(adminLoggerList.size(),101);
//    }

}
