package edu.ecnu.scsse.pizza.consumer.server.controller;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.consumer.server.exception.PayFailureException;
import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.order.*;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrderRequest;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrderResponse;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrdersRequest;
import edu.ecnu.scsse.pizza.consumer.server.model.order.FetchOrdersResponse;
import edu.ecnu.scsse.pizza.consumer.server.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Controller
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 获取单个订单详情
     */
    @RequestMapping(value = "/fetchOrder", method = RequestMethod.POST)
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
    @RequestMapping(value = "/fetchOrders", method = RequestMethod.POST)
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
        } catch (ConsumerServerException e) {
            return new FetchOrdersResponse(e);
        }
    }

    /**
     * 获取菜单和购物车
     */
    @RequestMapping(value = "/fetchMenu", method = RequestMethod.POST)
    @ResponseBody
    public FetchMenuResponse fetchMenu(@RequestBody FetchMenuRequest request) {
        FetchMenuResponse response = new FetchMenuResponse();
        response.setPizzas(orderService.getInSaleMenu());
        try {
            response.setCart(orderService.getCartOrder(request.getUserId(), response.getPizzas()));
        } catch (ConsumerServerException e) {
            response.setException(e);
        }
        return response;
    }

    /**
     * 更新购物车商品
     */
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
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
    @RequestMapping(value = "/sendOrder", method = RequestMethod.POST)
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
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
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

        } catch (ConsumerServerException e) {
            return new CancelOrderResponse(e);
        }
    }

    /**
     * 获取服务电话、商家电话、配送员电话
     */
    @RequestMapping(value = "/fetchPhone", method = RequestMethod.POST)
    @ResponseBody
    public FetchPhoneResponse fetchPhone(@RequestBody FetchPhoneRequest request) {
        try {
            OrderService.Phones phones = orderService.getPhones(request.getOrderId());
            FetchPhoneResponse response = new FetchPhoneResponse();
            response.setDeliverymanPhone(phones.getDeliverymanPhone());
            response.setServicePhone(phones.getServicePhone());
            response.setShopPhone(phones.getShopPhone());
            return response;
        } catch (ConsumerServerException e) {
            return new FetchPhoneResponse(e);
        }

    }

    /**
     * 发起订单支付请求
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    public PayOrderResponse pay(@RequestBody PayOrderRequest request) {
        try {
            String form = orderService.payRequest(request.getOrderId(), request.getTotalPrice());
            PayOrderResponse response = new PayOrderResponse();
            response.setForm(form);
            return response;
        } catch (ConsumerServerException e) {
            return new PayOrderResponse(e);
        }

    }

    /**
     * 支付宝 notify_url（异步通知），表示支付完成，可更新数据库状态。
     */
    @RequestMapping(value = "/paid", method = RequestMethod.POST)
    @ResponseBody
    public String paid(HttpServletRequest request) {
        orderService.paid(request);
        return "success";
    }
}
