package edu.ecnu.scsse.pizza.data.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "deliver_ingredient_logger", schema = "pizza_project", catalog = "")
public class DeliverIngredientLoggerEntity {
    private int id;
    private Integer shopId;
    private Integer ingredientId;
    private Integer count;
    private Timestamp time;
    private Integer adminId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shop_id")
    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "ingredient_id")
    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Basic
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "admin_id")
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliverIngredientLoggerEntity that = (DeliverIngredientLoggerEntity) o;

        if (id != that.id) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (ingredientId != null ? !ingredientId.equals(that.ingredientId) : that.ingredientId != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (adminId != null ? !adminId.equals(that.adminId) : that.adminId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (ingredientId != null ? ingredientId.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (adminId != null ? adminId.hashCode() : 0);
        return result;
    }
}
