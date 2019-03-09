package edu.ecnu.scsse.pizza.bussiness.server.model.gaode;

public class BicyclingStep {
    private String instruction;
    private String road;
    private double distance;
    private String orientation;
    private double duration;
    private String polyline;
    private String action;
    private String assistantAction;

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAssistantAction() {
        return assistantAction;
    }

    public void setAssistantAction(String assistantAction) {
        this.assistantAction = assistantAction;
    }

    public BicyclingStep() {
    }

    public BicyclingStep(String instruction, String road, double distance, String orientation, double duration, String polyline, String action, String assistantAction) {
        this.instruction = instruction;
        this.road = road;
        this.distance = distance;
        this.orientation = orientation;
        this.duration = duration;
        this.polyline = polyline;
        this.action = action;
        this.assistantAction = assistantAction;
    }
}
