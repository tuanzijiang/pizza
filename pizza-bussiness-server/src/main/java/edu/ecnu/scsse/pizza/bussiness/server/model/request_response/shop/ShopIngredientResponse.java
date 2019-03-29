package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

import java.util.List;

public class ShopIngredientResponse extends BaseResponse{
    private List<Ingredient> ingredientList;

    public ShopIngredientResponse(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public ShopIngredientResponse(BusinessServerException e) {
        super(e);
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
