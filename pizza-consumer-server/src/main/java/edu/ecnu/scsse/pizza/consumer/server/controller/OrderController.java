package edu.ecnu.scsse.pizza.consumer.server.controller;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.order.*;
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

import java.util.List;

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
            Order order = orderService.fetchOrder(request.getOrderId());
            FetchOrderResponse response = new FetchOrderResponse();
            response.setOrder(order);
            return response;
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
            List<Order> orders = orderService.fetchOrders(request.getUserId(),
                    request.getStatus(),
                    request.getLastOrderId(),
                    request.getNum());
            FetchOrdersResponse response = new FetchOrdersResponse();
            response.setOrders(orders);
            return response;
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
        response.setPizzas(orderService.getInSaleMenu());
        try {
            response.setCart(orderService.getCartOrder(request.getUserId()));
        } catch (IllegalArgumentException e) {
            response.setException(e);
        }
        return response;
    }

    /**
     * 更新购物车商品
     */
    @RequestMapping("/updateOrder")
    @ResponseBody
    public UpdateOrderResponse updateOrder(@RequestBody UpdateOrderRequest request) {
        UpdateOrderResponse response = new UpdateOrderResponse();
        try {
            if (orderService.updateOrder(request.getOrderId(), request.getPizzaId(), request.getCount()) <= 0) {
                response.setResultType(ResultType.FAILURE);
                response.setErrorMsg("无法更改商品数量。");
            }
        } catch (ConsumerServerException e) {
            response.setException(e);
        }
        return response;
    }

    /**
     * 确认订单
     */
    @RequestMapping("/sendOrder")
    @ResponseBody
    public SendOrderResponse sendOrder(@RequestBody SendOrderRequest request) {
        try {
            Order order = orderService.sendOrder(request.getOrderId(), request.getUserAddressId());
            SendOrderResponse response = new SendOrderResponse();
            response.setOrder(order);
            return response;
        } catch (ConsumerServerException e) {
            return new SendOrderResponse(e);
        }
    }

    /**
     * 取消订单
     */
    @RequestMapping("/cancelOrder")
    @ResponseBody
    public CancelOrderResponse cancelOrder(@RequestBody CancelOrderRequest request) {
        try {
            if (orderService.cancelOrder(request.getOrderId())) {
                return new CancelOrderResponse();
            } else {
                CancelOrderResponse response = new CancelOrderResponse();
                response.setResultType(ResultType.FAILURE);
                response.setErrorMsg("订单确认超过10分钟，无法取消。");
                return response;
            }

        } catch (NotFoundException e) {
            return new CancelOrderResponse(e);
        }
    }

    /**
     * 获取服务电话、商家电话、配送员电话
     */
    @RequestMapping("/fetchPhone")
    @ResponseBody
    public FetchPhoneResponse fetchPhone(@RequestBody FetchPhoneRequest request) {
        try {
            OrderService.Phones phones = orderService.getPhones(request.getOrderId());
            FetchPhoneResponse response = new FetchPhoneResponse();
            response.setDeliverymanPhone(phones.getDeliverymanPhone());
            response.setServicePhone(phones.getServicePhone());
            response.setShopPhone(phones.getShopPhone());
            return response;
        } catch (NotFoundException e) {
            return new FetchPhoneResponse(e);
        }

    }
}
