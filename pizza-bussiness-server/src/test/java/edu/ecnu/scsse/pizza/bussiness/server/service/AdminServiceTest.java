package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginResponse;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class AdminServiceTest extends TestApplication{
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceTest.class);

    @Autowired
    private AdminService adminService;

    @Test
    public void testAdminLoginSuccessfully() {
        LoginRequest successRequest = new LoginRequest("admin","admin");
        LoginResponse response = adminService.adminLogin(successRequest);
        Assert.assertEquals(response.getAdminId(),1);
        Assert.assertEquals(response.getResultType(), ResultType.SUCCESS);
    }

    @Test
    public void testAdminLoginWrongPwd() {
        LoginRequest wrongPwdRequest = new LoginRequest("admin","123");
        LoginResponse response = adminService.adminLogin(wrongPwdRequest);
        Assert.assertEquals(response.getAdminId(),-1);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

    @Test
    public void testAdminLoginWrongAdminName() {
        LoginRequest wrongAdminNameRequest = new LoginRequest("123","admin");
        LoginResponse response = adminService.adminLogin(wrongAdminNameRequest);
        Assert.assertEquals(response.getAdminId(),-1);
        Assert.assertEquals(response.getResultType(), ResultType.FAILURE);
    }

}
