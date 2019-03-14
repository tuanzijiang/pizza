package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailRequest;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaTag;

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
    private PizzaTag tagName;
    private int count;
    private List<Ingredient> ingredients;
    private Map<Ingredient,Integer> ingredientIntegerMap;

    public Menu() {
        this.id = "";
        this.name = "";
        this.image = "";
        this.description = "";
        this.price = 0.0;
        this.state = PizzaStatus.IN_SALE;
        this.tag = -1;
        this.count = 0;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<Ingredient, Integer> getIngredientIntegerMap() {
        return ingredientIntegerMap;
    }

    public void setIngredientIntegerMap(Map<Ingredient, Integer> ingredientIntegerMap) {
        this.ingredientIntegerMap = ingredientIntegerMap;
    }

    public PizzaTag getTagName() {
        return tagName;
    }

    public void setTagName(PizzaTag tagName) {
        this.tagName = tagName;
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

    public Menu(String id, String name, String image, String description, double price, PizzaStatus state, PizzaTag tagName, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.state = state;
        this.tagName = tagName;
        this.ingredients = ingredients;
    }

    public Menu(MenuDetailRequest menu){
        this.id = menu.getId();
        this.name = menu.getName();
        this.image = menu.getImage();
        this.description = menu.getDescription();
        this.price = menu.getPrice();
        this.state = menu.getState();
        this.tagName = menu.getTagName();
    }

}
