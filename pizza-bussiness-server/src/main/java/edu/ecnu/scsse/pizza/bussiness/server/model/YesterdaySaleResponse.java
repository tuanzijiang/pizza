package edu.ecnu.scsse.pizza.bussiness.server.model;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.SaleStatus;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class YesterdaySaleResponse extends BaseResponse{
    private int orderNum;
    private int completeNum;
    private int cancelNum;
    private double totalAmount;

    public YesterdaySaleResponse() {
    }


    public YesterdaySaleResponse(SaleStatus saleStatus) {
        BeanUtils.copyProperties(saleStatus,this);
    }

    public YesterdaySaleResponse(BusinessServerException e) {
        super(e);
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
}
