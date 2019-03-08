package edu.ecnu.scsse.pizza.bussiness.server.model;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;

public class OrderDetailResponse extends BaseResponse{
    private Order order;

    public OrderDetailResponse(){

    }

    public OrderDetailResponse(Order order) {
        this.order = order;
    }

    public OrderDetailResponse(BusinessServerException e) {
        super(e);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
