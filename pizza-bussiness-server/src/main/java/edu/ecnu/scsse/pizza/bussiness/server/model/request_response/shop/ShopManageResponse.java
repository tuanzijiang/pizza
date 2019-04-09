package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.*;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailResponse;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;

import java.sql.Timestamp;
import java.util.List;

public class ShopManageResponse{
    private int id;
    private List<Driver> driverList;
    private List<Order> orderList;
    private List<IngredientEntity> ingredientList;
    private Point mapPoint;
    private int maxNum;
    private Timestamp startTime;
    private Timestamp endTime;
    private String name;
    private String address;
    private String phone;
    private String image;
    private String openHours;
    private double lat;
    private double lon;

    public ShopManageResponse() {
    }

    public ShopManageResponse(int id, List<Driver> driverList, List<Order> orderList, List<IngredientEntity> ingredientList, Point mapPoint, int maxNum, Timestamp startTime, Timestamp endTime, String name, String address, String phone, String image, String openHours, double lat, double lon) {
        this.id = id;
        this.driverList = driverList;
        this.orderList = orderList;
        this.ingredientList = ingredientList;
        this.mapPoint = mapPoint;
        this.maxNum = maxNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.openHours = openHours;
        this.lat = lat;
        this.lon = lon;
    }

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

    public List<IngredientEntity> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<IngredientEntity> ingredientList) {
        this.ingredientList = ingredientList;
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

    public String getOpenHours() {
        return openHours;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
