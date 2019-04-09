package edu.ecnu.scsse.pizza.data.bean;

public class DriverBean {
    private Integer id;
    private String name;
    private String phone;
    private Integer state;
    private Integer shopId;
    private String shopName;

    public DriverBean() {
    }

    public DriverBean(Integer id, String name, String phone, Integer state, Integer shopId, String shopName) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.state = state;
        this.shopId = shopId;
        this.shopName = shopName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
