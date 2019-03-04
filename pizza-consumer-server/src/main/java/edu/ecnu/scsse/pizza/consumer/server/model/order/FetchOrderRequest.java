package edu.ecnu.scsse.pizza.consumer.server.model.order;

public class FetchOrderRequest {

    private Integer userId;

    private String orderId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
