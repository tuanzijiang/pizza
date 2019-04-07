package edu.ecnu.scsse.pizza.consumer.server.utils;

import edu.ecnu.scsse.pizza.data.domain.OrderEntity;

import java.io.Serializable;

public class OrderReceiveResponse extends BaseResponse implements Serializable {
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

    public OrderReceiveResponse() {
    }

    @Override
    public String toString() {
        return "OrderReceiveResponse{" +
                "orderEntity=" + orderEntity +
                '}';
    }
}
