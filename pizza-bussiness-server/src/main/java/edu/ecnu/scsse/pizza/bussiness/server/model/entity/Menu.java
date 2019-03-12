package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;

import java.util.List;
import java.util.Map;

public class Menu {
    private String id;
    private String name;
    private String image;
    private String description;
    private double price;
    private PizzaStatus state;
    private int tag;
    private int count;
    private Map<Ingredient,Integer> ingredientIntegerMap;

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

    public PizzaStatus getState() {
        return state;
    }

    public void setState(PizzaStatus state) {
        this.state = state;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<Ingredient, Integer> getIngredientIntegerMap() {
        return ingredientIntegerMap;
    }

    public void setIngredientIntegerMap(Map<Ingredient, Integer> ingredientIntegerMap) {
        this.ingredientIntegerMap = ingredientIntegerMap;
    }

    public Menu(String id, String name, String image, String description, double price, PizzaStatus state, int tag, int count, Map<Ingredient, Integer> ingredientIntegerMap) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.state = state;
        this.tag = tag;
        this.count = count;
        this.ingredientIntegerMap = ingredientIntegerMap;
    }

    public Menu() {
    }
}
