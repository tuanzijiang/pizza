package edu.ecnu.scsse.pizza.bussiness.server.model.gaode;

import java.util.List;

public class BicyclingPath {
    private double distance;
    private double duration;
    private List<BicyclingStep> bicyclingStepList;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public List<BicyclingStep> getBicyclingStepList() {
        return bicyclingStepList;
    }

    public void setBicyclingStepList(List<BicyclingStep> bicyclingStepList) {
        this.bicyclingStepList = bicyclingStepList;
    }

    public BicyclingPath() {
    }

    public BicyclingPath(double distance, double duration, List<BicyclingStep> bicyclingStepList) {
        this.distance = distance;
        this.duration = duration;
        this.bicyclingStepList = bicyclingStepList;
    }
}
