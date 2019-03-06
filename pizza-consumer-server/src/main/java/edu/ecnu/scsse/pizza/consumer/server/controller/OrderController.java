package edu.ecnu.scsse.pizza.consumer.server.controller;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrderRequest;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrderResponse;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrdersRequest;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrdersResponse;
import edu.ecnu.scsse.pizza.consumer.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取单个订单详情
     */
    @RequestMapping("/fetchOrder")
    @ResponseBody
    public FetchOrderResponse fetchOrder(@RequestBody FetchOrderRequest request) {
        if (request.getOrderId() != null) {
            return orderService.fetchOrder(request.getOrderId());
        } else {
            return orderService.createCartOrder(request.getUserId());
        }
    }

    /**
     * 分页批量获取订单详情
     */
    @RequestMapping("/fetchOrders")
    @ResponseBody
    public FetchOrdersResponse fetchOrders(@RequestBody FetchOrdersRequest request) {
        // todo
        return new FetchOrdersResponse();
    }

}
