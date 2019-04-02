package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import edu.ecnu.scsse.pizza.data.enums.IngredientStatus;

public class IngredientDetailResponse{
    private int id;
    private String name;
    private String supplierName;
    private int state;
    private int alertNum;
    private int count;
    private int menuNeedCount;
    private String ingredientStatus;

    public IngredientDetailResponse() {
    }

    public IngredientDetailResponse(int id, String name, String supplierName, int state, int alertNum, int count, int menuNeedCount, String ingredientStatus) {
        this.id = id;
        this.name = name;
        this.supplierName = supplierName;
        this.state = state;
        this.alertNum = alertNum;
        this.count = count;
        this.menuNeedCount = menuNeedCount;
        this.ingredientStatus = ingredientStatus;
    }

    public IngredientDetailResponse(IngredientEntity entity) {
        this.id = 0;
        this.name = "";
        this.supplierName = "";
        this.state = 0;
        this.alertNum = 0;
        this.count = 0;
        this.menuNeedCount = 0;
        this.ingredientStatus = IngredientStatus.USING.getExpression();
        CopyUtils.copyProperties(entity,this);
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

    public int getMenuNeedCount() {
        return menuNeedCount;
    }

    public void setMenuNeedCount(int menuNeedCount) {
        this.menuNeedCount = menuNeedCount;
    }

    public String getIngredientStatus() {
        return ingredientStatus;
    }

    public void setIngredientStatus(String ingredientStatus) {
        this.ingredientStatus = ingredientStatus;
    }
}
