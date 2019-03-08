package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.model.*;
import edu.ecnu.scsse.pizza.bussiness.server.model.OrderDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.OrderDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.*;
import edu.ecnu.scsse.pizza.bussiness.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import edu.ecnu.scsse.pizza.bussiness.server.model.OrderManageResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
     * @param request
     * @return response
     */
    @RequestMapping(value = "/getOrderDetail",method = RequestMethod.GET)
    @ResponseBody
    public OrderDetailResponse getOrderDetail(@RequestBody OrderDetailRequest request){
        return orderService.getOrderDetail(request);
    }

    /**
     * 查看昨日订单指标
     * @return response
     */
    @RequestMapping(value = "/getYesterdaySaleStatus",method = RequestMethod.GET)
    @ResponseBody
    public SaleResponse getYesterdaySaleStatus() throws ParseException {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE,-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = sdf.format(calendar.getTime());
        return orderService.getSaleStatusList(yesterday,yesterday);
    }

    /**
     * 查看月订单指标
     * @param request
     * @return response
     */
    @RequestMapping(value = "/getMonthSaleStatus",method = RequestMethod.GET)
    @ResponseBody
    public SaleResponse getMonthSaleStatus(@RequestBody SaleRequest request) throws ParseException {
        String startDate = request.getStartDate();
        String endDate = request.getEndDate();
        return orderService.getSaleStatusList(startDate,endDate);
    }


}
