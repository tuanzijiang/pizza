package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeliverySchedule implements Comparable<DeliverySchedule>{
    private List<Order> orderList;
    private int score;
    private boolean ableToDelivery;
    private Map<String,Long> deliveryTimeEachOrderId;
    private long sumDeliveryDuration;
    private Timestamp minLatestLeaveTime;
    private long backDuration;
    private int driverId;

    @Override
    public int compareTo(DeliverySchedule deliverySchedule) {
        int num = deliverySchedule.getScore() -this.score;
        if(num==0){
            return deliverySchedule.getOrderList().size()- this.orderList.size();
        }
        return num;
    }

    public DeliverySchedule() {
    }

    public DeliverySchedule(List<Order> orderList, int score, boolean ableToDelivery, Map<String, Long> deliveryTimeEachOrderId, Timestamp minLatestLeaveTime, long backDuration, int driverId) {
        this.orderList = orderList;
        this.score = score;
        this.ableToDelivery = ableToDelivery;
        this.deliveryTimeEachOrderId = deliveryTimeEachOrderId;
        this.minLatestLeaveTime = minLatestLeaveTime;
        this.backDuration = backDuration;
        this.driverId = driverId;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isAbleToDelivery() {
        return ableToDelivery;
    }

    public void setAbleToDelivery(boolean ableToDelivery) {
        this.ableToDelivery = ableToDelivery;
    }

    public Map<String, Long> getDeliveryTimeEachOrderId() {
        return deliveryTimeEachOrderId;
    }

    public void setDeliveryTimeEachOrderId(Map<String, Long> deliveryTimeEachOrderId) {
        this.deliveryTimeEachOrderId = deliveryTimeEachOrderId;
    }

    public Timestamp getMinLatestLeaveTime() {
        return minLatestLeaveTime;
    }

    public void setMinLatestLeaveTime(Timestamp minLatestLeaveTime) {
        this.minLatestLeaveTime = minLatestLeaveTime;
    }

    public long getBackDuration() {
        return backDuration;
    }

    public void setBackDuration(long backDuration) {
        this.backDuration = backDuration;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public long getSumDeliveryDuration() {
        return sumDeliveryDuration;
    }

    public void setSumDeliveryDuration(long sumDeliveryDuration) {
        this.sumDeliveryDuration = sumDeliveryDuration;
    }

    @Override
    public String toString() {
        return "score="+score+", minLatestLeaveTime=" + minLatestLeaveTime +", driverId=" + driverId +", backDuration=" + backDuration ;
    }
}
