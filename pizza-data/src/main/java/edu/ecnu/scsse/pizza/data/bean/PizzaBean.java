package edu.ecnu.scsse.pizza.data.bean;

public class PizzaBean {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer tag;
    private String img;
    private Integer state;
    private Integer count;

    public PizzaBean() {
    }

    public PizzaBean(Integer id, String name, String description, Double price, Integer tag, String img, Integer state, Integer count) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.tag = tag;
        this.img = img;
        this.state = state;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
