package edu.ecnu.scsse.pizza.bussiness.server.model.entity;


import java.sql.Timestamp;
import java.util.List;

public class Shop {
    private int id;
    private List<Driver> driverList;
    private List<Order> orderList;
    private Point mapPoint;
    private int maxNum;
    private Timestamp startTime;
    private Timestamp endTime;
    private String name;
    private String address;
    private String phone;
    private String image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Point getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(Point mapPoint) {
        this.mapPoint = mapPoint;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Shop() {
    }

    public Shop(int id, List<Driver> driverList, List<Order> orderList, Point mapPoint, int maxNum, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.driverList = driverList;
        this.orderList = orderList;
        this.mapPoint = mapPoint;
        this.maxNum = maxNum;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
