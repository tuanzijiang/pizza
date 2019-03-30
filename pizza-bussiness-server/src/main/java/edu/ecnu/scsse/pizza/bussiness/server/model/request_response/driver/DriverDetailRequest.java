package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver;

public class DriverDetailRequest {
    private int driverId;
    private String name;
    private String phone;
    private int shopId;

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public DriverDetailRequest() {
    }

    public DriverDetailRequest( String name, String phone, int shopId) {
        this.name = name;
        this.phone = phone;
        this.shopId = shopId;
    }

    public DriverDetailRequest(int driverId, String name, String phone, int shopId) {
        this.driverId = driverId;
        this.name = name;
        this.phone = phone;
        this.shopId = shopId;
    }
}
