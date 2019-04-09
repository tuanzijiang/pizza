package edu.ecnu.scsse.pizza.data.bean;

public class IngredientBean {
    private Integer id;
    private String name;
    private String supplierName;
    private Integer state;
    private Integer alertNum;
    private Integer count;
    private Integer menuNeedCount;

    public IngredientBean() {
    }

    public IngredientBean(Integer id, String name, String supplierName, Integer state, Integer alertNum, Integer count, Integer menuNeedCount) {
        this.id = id;
        this.name = name;
        this.supplierName = supplierName;
        this.state = state;
        this.alertNum = alertNum;
        this.count = count;
        this.menuNeedCount = menuNeedCount;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAlertNum() {
        return alertNum;
    }

    public void setAlertNum(Integer alertNum) {
        this.alertNum = alertNum;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMenuNeedCount() {
        return menuNeedCount;
    }

    public void setMenuNeedCount(Integer menuNeedCount) {
        this.menuNeedCount = menuNeedCount;
    }
}
