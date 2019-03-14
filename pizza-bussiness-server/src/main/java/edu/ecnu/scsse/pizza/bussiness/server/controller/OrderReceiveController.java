package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.OrderReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/orderReceive")
public class OrderReceiveController {
    @Autowired
    private OrderReceiveService orderReceiveService;

    /**
     * 判断商家是否接单，并返回接单信息
     */
    @RequestMapping(value= "/getReceiveShopId",method = RequestMethod.GET)
    @ResponseBody
    public OrderReceiveResponse getReceiveShopId(@RequestBody OrderReceiveRequest orderReceiveRequest){
        return orderReceiveService.getReceiveShopId(orderReceiveRequest);
    }
}
