package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

public class Ingredient {
    private int id;
    private String name;
    private String supplierName;
    private int state;
    private int alermNum;
    private int count;

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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public Ingredient(int id, String name, String supplierName, int state, int alermNum, int count) {
        this.id = id;
        this.name = name;
        this.supplierName = supplierName;
        this.state = state;
        this.alermNum = alermNum;
        this.count = count;
    }

    public Ingredient() {
    }
}
