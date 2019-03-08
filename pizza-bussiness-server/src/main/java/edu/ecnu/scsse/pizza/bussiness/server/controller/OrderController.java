package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.model.OrderManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 查看用户信息列表
     */
    @RequestMapping(value = "/getOrderList",method = RequestMethod.GET)
    @ResponseBody
    public OrderManageResponse getOrderList(){
        return orderService.getOrderList();
    }
}
