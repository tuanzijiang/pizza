package edu.ecnu.scsse.pizza.data.domain;

import javax.persistence.*;

@Entity
@Table(name = "menu_ingredient", schema = "pizza_project", catalog = "")
public class MenuIngredientEntity {
    private int id;
    private Integer menuId;
    private Integer ingredientId;
    private Integer count;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "menu_id")
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuIngredientEntity that = (MenuIngredientEntity) o;

        if (id != that.id) return false;
        if (menuId != null ? !menuId.equals(that.menuId) : that.menuId != null) return false;
        if (ingredientId != null ? !ingredientId.equals(that.ingredientId) : that.ingredientId != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (menuId != null ? menuId.hashCode() : 0);
        result = 31 * result + (ingredientId != null ? ingredientId.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }
}
