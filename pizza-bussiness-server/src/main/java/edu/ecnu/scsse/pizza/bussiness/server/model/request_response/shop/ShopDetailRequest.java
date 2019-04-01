package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop;

public class ShopDetailRequest {
    private String id;
    private int maxNum;
    private String name;
    private String address;
    private String phone;
    private String image;
    private String startTime;
    private String endTime;
    private double lat;
    private double lon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public ShopDetailRequest( int maxNum, String name, String address, String phone, String image, String startTime, String endTime, double lat, double lon) {
        this.maxNum = maxNum;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lat = lat;
        this.lon = lon;
    }

    public ShopDetailRequest(String id, int maxNum, String name, String address, String phone, String image, String startTime, String endTime, double lat, double lon) {
        this.id = id;
        this.maxNum = maxNum;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lat = lat;
        this.lon = lon;
    }

    public ShopDetailRequest() {
    }
}
