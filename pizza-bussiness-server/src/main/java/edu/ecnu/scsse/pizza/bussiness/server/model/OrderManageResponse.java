package edu.ecnu.scsse.pizza.bussiness.server.model;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;

import java.util.List;

public class OrderManageResponse extends BaseResponse{
    private List<Order> orderList;

    public OrderManageResponse(){

    }

    public OrderManageResponse(List<Order> orderList) {
        this.orderList = orderList;
    }

    public OrderManageResponse(BusinessServerException e) {
        super(e);
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
