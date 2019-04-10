package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

public class ShopIngredient {
    private int id;
    private String name;
    private int shopId;
    private String shopName;
    private int alertNum;
    private int count;

    public ShopIngredient() {
    }

    public ShopIngredient(int id, String name, int shopId, String shopName, int alertNum, int count) {
        this.id = id;
        this.name = name;
        this.shopId = shopId;
        this.shopName = shopName;
        this.alertNum = alertNum;
        this.count = count;
    }

    public ShopIngredient(Integer id, String name, Integer shopId, String shopName, Integer alertNum, Integer count) {
        this.id = id;
        this.name = name;
        this.shopId = shopId;
        this.shopName = shopName;
        this.alertNum = alertNum;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getAlertNum() {
        return alertNum;
    }

    public void setAlertNum(int alertNum) {
        this.alertNum = alertNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
