package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;

public class OrderReceiveResponse extends BaseResponse {
    private OrderEntity orderEntity;

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public OrderReceiveResponse(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public OrderReceiveResponse(BusinessServerException e, OrderEntity orderEntity) {
        super(e);
        this.orderEntity = orderEntity;
    }
    public OrderReceiveResponse(BusinessServerException e){
        super(e);
    }

    public OrderReceiveResponse() {
    }

    @Override
    public String toString() {
        return "OrderReceiveResponse{" +
                "orderEntity=" + orderEntity +
                '}';
    }
}
