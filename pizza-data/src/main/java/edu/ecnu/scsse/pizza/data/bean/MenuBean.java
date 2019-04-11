package edu.ecnu.scsse.pizza.data.bean;

public class MenuBean {
    private Integer menuId;
    private String name;
    private String image;
    private String description;
    private Double price;
    private Integer state;
    private Integer tag;
    private Integer count;

    public MenuBean() {
    }

    public MenuBean(Integer menuId, String name, String image, String description, Double price, Integer state, Integer tag, Integer count) {
        this.menuId = menuId;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.state = state;
        this.tag = tag;
        this.count = count;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
