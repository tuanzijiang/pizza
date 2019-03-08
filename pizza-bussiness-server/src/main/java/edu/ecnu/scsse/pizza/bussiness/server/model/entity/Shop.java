package edu.ecnu.scsse.pizza.bussiness.server.model.entity;


import java.util.List;

public class Shop {
    private int id;
    private List<Driver> driverList;
    private List<Order> orderList;
    private Point mapPoint;

    public double get_distance(Point point){
        MapUtil mapUtil=new MapUtil();
        return mapUtil.distanceOf(point,mapPoint);
    }
}
