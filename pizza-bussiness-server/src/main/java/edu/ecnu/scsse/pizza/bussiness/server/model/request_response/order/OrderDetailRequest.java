package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order;

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
