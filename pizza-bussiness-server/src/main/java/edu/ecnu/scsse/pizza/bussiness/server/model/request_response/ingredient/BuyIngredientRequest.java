package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient;

public class BuyIngredientRequest {
    private int ingredientId;
    private int shopId;
    private int orderNum;

    public BuyIngredientRequest() {
    }

    public BuyIngredientRequest(int ingredientId, int shopId, int orderNum) {
        this.ingredientId = ingredientId;
        this.shopId = shopId;
        this.orderNum = orderNum;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
