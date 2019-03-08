package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

public class MapPoint implements Point{
    private double latitude,longitude;
    public MapPoint(double x,double y){
        this.latitude=x;
        this.longitude=y;
    }
    @Override
    public double x(){
        return latitude;
    }
    @Override
    public double y(){
        return longitude;
    }

}
