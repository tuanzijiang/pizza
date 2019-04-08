package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailResponse;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaTag;

import java.util.ArrayList;
import java.util.List;

public class MenuDetailResponse{
    private String id;
    private String name;
    private String image;
    private String description;
    private double price;
    private String state;
    private String tagName;
    private int count;
    private List<IngredientDetailResponse> ingredients;

    public MenuDetailResponse() {
        this.id = "";
        this.name = "";
        this.image = "";
        this.description = "";
        this.price = 0.0;
        this.state = PizzaStatus.IN_SALE.getExpression();
        this.tagName = PizzaTag.UNKNOWN.getExpression();
        this.count = 0;
        this.ingredients = new ArrayList<>();
    }

    public MenuDetailResponse(String id, String name, String image, String description, double price, String state, String tagName, int count, List<IngredientDetailResponse> ingredients) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.state = state;
        this.tagName = tagName;
        this.count = count;
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<IngredientDetailResponse> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDetailResponse> ingredients) {
        this.ingredients = ingredients;
    }
}
