package edu.ecnu.scsse.pizza.bussiness.server.model;

public class OrderDetailRequest {
    private int orderId;

    public OrderDetailRequest() {
    }

    public OrderDetailRequest(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
