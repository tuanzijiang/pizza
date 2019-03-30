package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderReceiveControllerTest extends TestApplication{
    @Autowired
    OrderReceiveController orderReceiveController;

//    @Test
//    public void testGetReceiveShopId(){
//        OrderReceiveRequest orderReceiveRequest=new OrderReceiveRequest("BBB",8);
//        OrderReceiveResponse orderReceiveResponse=orderReceiveController.getReceiveShopId(orderReceiveRequest);
//        Assert.assertEquals(orderReceiveResponse.getResultType(), ResultType.SUCCESS);
//    }
}
