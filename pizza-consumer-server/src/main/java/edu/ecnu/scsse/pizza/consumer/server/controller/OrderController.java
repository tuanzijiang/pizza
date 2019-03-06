package edu.ecnu.scsse.pizza.consumer.server.controller;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.order.*;
import edu.ecnu.scsse.pizza.consumer.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        try {
            return orderService.fetchOrder(request.getOrderId());
        } catch (ConsumerServerException e) {
            return new FetchOrderResponse(e);
        }
    }

    /**
     * 分页批量获取订单详情
     */
    @RequestMapping("/fetchOrders")
    @ResponseBody
    public FetchOrdersResponse fetchOrders(@RequestBody FetchOrdersRequest request) {
        try {
            return orderService.fetchOrders(request.getUserId(),
                    request.getStatus(),
                    request.getLastOrderId(),
                    request.getNum());
        } catch (NotFoundException | IllegalArgumentException e) {
            return new FetchOrdersResponse(e);
        }
    }

    /**
     * 获取菜单和购物车
     */
    @RequestMapping("/fetchMenu")
    @ResponseBody
    public FetchMenuResponse fetchMenu(@RequestBody FetchMenuRequest request) {
        FetchMenuResponse response = new FetchMenuResponse();
        response.setPizzas(orderService.getAllMenu());
        try {
            response.setCart(orderService.getCartOrder(request.getUserId()));
        } catch (IllegalArgumentException e) {
            response.setException(e);
        }
        return response;
    }
}
