package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.model.OrderDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.OrderDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.OrderManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 查看订单信息列表
     */
    @RequestMapping(value = "/getOrderList",method = RequestMethod.GET)
    @ResponseBody
    public OrderManageResponse getOrderList(){
        return orderService.getOrderList();
    }

    /**
     * 查看订单详情
     * @request orderId
     * @return
     */
    @RequestMapping(value = "/getOrderDetail",method = RequestMethod.GET)
    @ResponseBody
    public OrderDetailResponse getOrderDetail(@RequestBody OrderDetailRequest request){
        return orderService.getOrderDetail(request);
    }

}
