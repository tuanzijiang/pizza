package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import com.alibaba.fastjson.JSONObject;

public class SaleStatus{
    private String date;
    private int orderNum;
    private int completeNum;
    private int cancelNum;
    private double totalAmount;

    public SaleStatus() {
    }

    public SaleStatus(String date, int orderNum, int completeNum, int cancelNum, double totalAmount) {
        this.date = date;
        this.orderNum = orderNum;
        this.completeNum = completeNum;
        this.cancelNum = cancelNum;
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(int completeNum) {
        this.completeNum = completeNum;
    }

    public int getCancelNum() {
        return cancelNum;
    }

    public void setCancelNum(int cancelNum) {
        this.cancelNum = cancelNum;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}
