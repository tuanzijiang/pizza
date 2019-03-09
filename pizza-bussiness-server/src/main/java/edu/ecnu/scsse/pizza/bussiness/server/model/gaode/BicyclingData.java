package edu.ecnu.scsse.pizza.bussiness.server.model.gaode;

import java.util.List;

public class BicyclingData {
    private String origin;
    private String destination;
    private List<BicyclingPath> bicyclingPathList;
    private int errcode;
    private String errdetail;
    private String errmsg;

    public double total_duation(){
        if(errcode ==0){
            double total=0.0;
            for (BicyclingPath bicyclingPath:bicyclingPathList){
                total += bicyclingPath.getDuration();
            }
            return total;
        }
        else {
            return -1;
        }
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<BicyclingPath> getBicyclingPathList() {
        return bicyclingPathList;
    }

    public void setBicyclingPathList(List<BicyclingPath> bicyclingPathList) {
        this.bicyclingPathList = bicyclingPathList;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrdetail() {
        return errdetail;
    }

    public void setErrdetail(String errdetail) {
        this.errdetail = errdetail;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public BicyclingData() {
    }

    public BicyclingData(String origin, String destination, List<BicyclingPath> bicyclingPathList, int errcode, String errdetail, String errmsg) {
        this.origin = origin;
        this.destination = destination;
        this.bicyclingPathList = bicyclingPathList;
        this.errcode = errcode;
        this.errdetail = errdetail;
        this.errmsg = errmsg;
    }
}
