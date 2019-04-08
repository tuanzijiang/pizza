package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.PendingOrder;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.*;
import edu.ecnu.scsse.pizza.bussiness.server.service.OrderService;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 查看订单信息列表
     */
    @RequestMapping(value = "/getOrderList",method = RequestMethod.GET)
    @ResponseBody
    public List<OrderManageResponse> getOrderList(){
        return orderService.getOrderList();
    }

//    /**
//     * 查看订单详情
//     * @param orderId
//     * @return response
//     */
//    @RequestMapping(value = "/getOrderDetail",method = RequestMethod.GET)
//    @ResponseBody
//    public OrderDetailResponse getOrderDetail(@RequestParam int orderId){
//        return orderService.getOrderDetail(orderId);
//    }

    /**
     * 查看昨日订单指标
     * @return response
     */
    @RequestMapping(value = "/getYesterdaySaleStatus",method = RequestMethod.GET)
    @ResponseBody
    public YesterdaySaleResponse getYesterdaySaleStatus() throws ParseException {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE,-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String yesterday = sdf.format(calendar.getTime());
        return orderService.getYesterdaySaleStatus(yesterday);
    }

    /**
     * 查看月订单指标
     * @param request
     * @return response
     */
    @RequestMapping(value = "/getMonthSaleStatus",method = RequestMethod.POST)
    @ResponseBody
    public SaleResponse getMonthSaleStatus(@RequestBody SaleRequest request) throws ParseException {
        String startDate = request.getStartDate();
        String endDate = request.getEndDate();
        return orderService.getSaleStatusList(startDate,endDate);
    }

    /**
     * 查看待审核请求
     */
    @RequestMapping(value = "/getPendingRequestList",method = RequestMethod.GET)
    @ResponseBody
    public List<PendingOrder> getPendingRequestList(){
        return orderService.getPendingRequestList();
    }

    /**
     * 通过审核
     */
    @RequestMapping(value = "/allowCancel",method = RequestMethod.GET)
    @ResponseBody
    public SimpleResponse allowCancel(@RequestParam int orderId){
        return orderService.changeOrderStatus(orderId, OrderStatus.CANCELED);
    }

    /**
     * 拒绝审核
     */
    @RequestMapping(value = "/denyCancel",method = RequestMethod.GET)
    @ResponseBody
    public SimpleResponse denyCancel(@RequestParam int orderId){
        return orderService.changeOrderStatus(orderId, OrderStatus.CANCEL_FAILED);
    }

    /**
     * 查看历史退单
     */
    @RequestMapping(value = "/getCancelOrderList",method = RequestMethod.GET)
    @ResponseBody
    public List<PendingOrder> getCancelOrderList(){
        return orderService.getCancelOrderList();
    }


}
