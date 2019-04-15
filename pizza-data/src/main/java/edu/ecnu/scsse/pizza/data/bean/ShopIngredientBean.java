package edu.ecnu.scsse.pizza.data.bean;

public class ShopIngredientBean {
    private Integer id;
    private String name;
    private Integer count;

    public ShopIngredientBean() {
    }

    public ShopIngredientBean(Integer id, String name, Integer count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
