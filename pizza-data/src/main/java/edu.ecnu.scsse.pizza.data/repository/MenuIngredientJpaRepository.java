package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.MenuIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuIngredientJpaRepository extends JpaRepository<MenuIngredientEntity,Integer> {
    List<MenuIngredientEntity> findByOrderId(int orderId);
    List<MenuIngredientEntity> findByMenuId(int menuId);
}