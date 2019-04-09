package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaTag;

import java.util.List;

public class MenuDetailRequest {
    private String id;
    private String name;
    private Object image;
    private String description;
    private List<Ingredient> ingredients;
    private double price;
    private String state;
    private String tagName;

    public MenuDetailRequest() {
    }

    public MenuDetailRequest( String name, Object image, String description, List<Ingredient> ingredients, double price, String state, String tagName) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.ingredients = ingredients;
        this.price = price;
        this.state = state;
        this.tagName = tagName;
    }

    public MenuDetailRequest(String id, String name, Object image, String description, List<Ingredient> ingredients, double price, String state, String tagName) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.ingredients = ingredients;
        this.price = price;
        this.state = state;
        this.tagName = tagName;
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

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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

}
