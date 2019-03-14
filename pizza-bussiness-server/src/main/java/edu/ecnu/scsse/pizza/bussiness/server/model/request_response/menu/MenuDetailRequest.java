package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaTag;

import java.util.List;

public class MenuDetailRequest {
    private String id;
    private String name;
    private String image;
    private String description;
    private List<Ingredient> ingredients;
    private double price;
    private PizzaStatus state;
    private PizzaTag tagName;

    public MenuDetailRequest() {
    }

    public MenuDetailRequest(String id, String name, String image, String description, List<Ingredient> ingredients, double price, PizzaStatus state, PizzaTag tagName) {
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

    public PizzaStatus getState() {
        return state;
    }

    public void setState(PizzaStatus state) {
        this.state = state;
    }

    public PizzaTag getTagName() {
        return tagName;
    }

    public void setTagName(PizzaTag tagName) {
        this.tagName = tagName;
    }

}
