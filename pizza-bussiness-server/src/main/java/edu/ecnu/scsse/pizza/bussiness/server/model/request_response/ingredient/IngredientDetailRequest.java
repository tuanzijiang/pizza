package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient;

import edu.ecnu.scsse.pizza.data.enums.IngredientStatus;

public class IngredientDetailRequest {
    private int id;
    private String name;
    private int count;
    private int alermNum;
    private String supplierName;
    private String status;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAlermNum() {
        return alermNum;
    }

    public void setAlermNum(int alarmNum) {
        this.alermNum = alarmNum;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public IngredientDetailRequest(String name, int count, int alermNum, String supplierName, String status) {
        this.name = name;
        this.count = count;
        this.alermNum = alermNum;
        this.supplierName = supplierName;
        this.status = status;
    }

    public IngredientDetailRequest(int id, String name, int count, int alermNum, String supplierName, String status) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.alermNum = alermNum;
        this.supplierName = supplierName;
        this.status = status;
    }

    public IngredientDetailRequest() {
    }
}
