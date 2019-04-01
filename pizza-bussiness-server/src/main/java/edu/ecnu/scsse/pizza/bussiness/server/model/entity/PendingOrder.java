package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import edu.ecnu.scsse.pizza.data.enums.OrderStatus;

public class PendingOrder {
    private String orderId;
    private String receiveName;
    private String receivePhone;
    private String commitTime;
    private double period;
    private String paidPeriod;
    private OrderStatus state;

    public PendingOrder() {
    }

    public PendingOrder(String orderId, String receiveName, String receivePhone, String commitTime, double period, String paidPeriod, OrderStatus state) {
        this.orderId = orderId;
        this.receiveName = receiveName;
        this.receivePhone = receivePhone;
        this.commitTime = commitTime;
        this.period = period;
        this.paidPeriod = paidPeriod;
        this.state = state;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getPaidPeriod() {
        return paidPeriod;
    }

    public void setPaidPeriod(String paidPeriod) {
        this.paidPeriod = paidPeriod;
    }

    public OrderStatus getState() {
        return state;
    }

    public void setState(OrderStatus state) {
        this.state = state;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }
}
