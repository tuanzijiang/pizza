package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

public class ShopIngredient {
    private int id;
    private String name;
    private int shopId;
    private String shopName;
    private int alermNum;
    private int count;

    public ShopIngredient() {
    }

    public ShopIngredient(int id, String name, int shopId, String shopName, int alermNum, int count) {
        this.id = id;
        this.name = name;
        this.shopId = shopId;
        this.shopName = shopName;
        this.alermNum = alermNum;
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

    public int getAlermNum() {
        return alermNum;
    }

    public void setAlermNum(int alermNum) {
        this.alermNum = alermNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
