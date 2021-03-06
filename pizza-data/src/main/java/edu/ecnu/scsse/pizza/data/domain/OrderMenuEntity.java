package edu.ecnu.scsse.pizza.data.domain;

import javax.persistence.*;

@Entity
@Table(name = "order_menu", schema = "pizza_project", catalog = "")
public class OrderMenuEntity {

    private Integer id;
    private int orderId;
    private int menuId;
    private Integer count;

    public OrderMenuEntity() {
    }

    public OrderMenuEntity(Integer id, int orderId, int menuId, Integer count) {
        this.id = id;
        this.orderId = orderId;
        this.menuId = menuId;
        this.count = count;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "menu_id")
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderMenuEntity that = (OrderMenuEntity) o;

        if (!id.equals(that.id)) return false;
        if (orderId != that.orderId) return false;
        if (menuId != that.menuId) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + orderId;
        result = 31 * result + menuId;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }
}
