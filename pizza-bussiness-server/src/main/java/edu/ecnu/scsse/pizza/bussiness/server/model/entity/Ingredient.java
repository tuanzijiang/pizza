package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import edu.ecnu.scsse.pizza.data.enums.IngredientStatus;

public class Ingredient {
    private int id;
    private String name;
    private String supplierName;
    private int state;
    private int alermNum;
    private int count;
    private int menuNeedCount;
    private IngredientStatus ingredientStatus;


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

    public int getMenuNeedCount() {
        return menuNeedCount;
    }

    public void setMenuNeedCount(int menuNeedCount) {
        this.menuNeedCount = menuNeedCount;
    }

    public IngredientStatus getIngredientStatus() {
        return ingredientStatus;
    }

    public void setIngredientStatus(IngredientStatus ingredientStatus) {
        this.ingredientStatus = ingredientStatus;
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
        this.id = 0;
        this.name = "";
        this.supplierName = "";
        this.state = 0;
        this.alermNum = 0;
        this.count = 0;
        this.menuNeedCount = 0;
        this.ingredientStatus= IngredientStatus.USING;
    }

    public Ingredient(IngredientEntity ingredientEntity){
        this.id = 0;
        this.name = "";
        this.supplierName = "";
        this.state = 0;
        this.alermNum = 0;
        this.count = 0;
        this.menuNeedCount = 0;
        CopyUtils.copyProperties(ingredientEntity,this);

    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", state=" + state +
                ", alermNum=" + alermNum +
                ", count=" + count +
                ", menuNeedCount=" + menuNeedCount +
                '}';
    }
}
