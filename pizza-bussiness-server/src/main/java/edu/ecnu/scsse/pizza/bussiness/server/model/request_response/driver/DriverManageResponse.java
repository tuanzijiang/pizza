package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Driver;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

import java.util.List;

public class DriverManageResponse{
    private int id;
    private int shopId;
    private String shopName;
    private String name;
    private String state;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
