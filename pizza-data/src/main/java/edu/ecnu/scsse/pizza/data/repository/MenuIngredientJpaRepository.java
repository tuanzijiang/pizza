package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.MenuIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MenuIngredientJpaRepository extends JpaRepository<MenuIngredientEntity,Integer> {
    //List<MenuIngredientEntity> findByOrderId(int orderId);
    List<MenuIngredientEntity> findByMenuId(int menuId);
    Optional<MenuIngredientEntity> findByMenuIdAndIngredientId(int menuId, int ingredientId);

    @Transactional
    @Modifying
    void deleteByMenuIdAndIngredientId(int menuId,int ingredientId);
}