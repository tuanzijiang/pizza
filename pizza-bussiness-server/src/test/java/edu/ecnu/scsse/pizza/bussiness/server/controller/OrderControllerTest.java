package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

public class OrderControllerTest extends TestApplication{
    private static final Logger logger = LoggerFactory.getLogger(OrderControllerTest.class);
    @Autowired
    OrderController orderController;

    @Test
    public void testGetOrderList() {
        List<Order> orderList = orderController.getOrderList();
        logger.info("Total order number is {}",orderList.size());
        Assert.assertEquals(3,orderList.size());
    }

    @Test
    public void testOrderDetail(){
        OrderDetailResponse orderDetailResponse = orderController.getOrderDetail(1);
        Assert.assertEquals(ResultType.SUCCESS,orderDetailResponse.getResultType());
    }

    @Test
    public void testYesterdaySaleStatus() throws ParseException{
        YesterdaySaleResponse response = orderController.getYesterdaySaleStatus();
        Assert.assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testMonthSaleStatus() throws ParseException{
        SaleRequest request = new SaleRequest("2019/03/01","2019/03/15");
        SaleResponse response = orderController.getMonthSaleStatus(request);
        Assert.assertEquals(ResultType.SUCCESS,response.getResultType());
        Assert.assertEquals(15,response.getSaleStatusList().size());
    }

}
