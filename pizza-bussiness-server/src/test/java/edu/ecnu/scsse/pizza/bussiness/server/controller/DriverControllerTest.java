package edu.ecnu.scsse.pizza.bussiness.server.controller;


import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Driver;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.AdminService;
import edu.ecnu.scsse.pizza.bussiness.server.service.DriverService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.text.ParseException;
import java.util.List;

public class DriverControllerTest extends TestApplication{
    private static final Logger logger = LoggerFactory.getLogger(DriverControllerTest.class);
    @Autowired
    DriverController driverController;
    @Autowired
    AdminService adminService;

    @Test
    public void testGetDriverList(){
        List<Driver> driverList=driverController.getDriverList();
        logger.info("Total order number is {}",driverList.size());
        Assert.assertEquals(3,driverList.size());
    }

    @Test
    public void testEditShopDetail() throws ParseException,BusinessServerException{
        DriverDetailRequest request=new DriverDetailRequest(2,"he","13162308626",2);
        DriverDetailResponse response=driverController.editShopDetail(request);
        Assert.assertEquals(ResultType.FAILURE,response.getResultType());
    }

    @Test
    public void testAddNewDriver() throws ParseException,BusinessServerException{
        DriverDetailRequest request=new DriverDetailRequest("Ni","13162308620",1);
        DriverDetailResponse response=driverController.addNewDriver(request);
        Assert.assertEquals(ResultType.FAILURE,response.getResultType());
    }

    @Test
    public void testEditShopDetailSuccessfully() throws ParseException,BusinessServerException{
        adminService.adminLogin(new LoginRequest("admin","admin"));
        DriverDetailRequest request=new DriverDetailRequest(2,"he","13162308626",2);
        DriverDetailResponse response=driverController.editShopDetail(request);
        Assert.assertEquals(ResultType.SUCCESS,response.getResultType());
    }

}
