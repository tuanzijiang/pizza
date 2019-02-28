package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.MenuIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuIngredientJpaRepository extends JpaRepository<MenuIngredientEntity,Long> {

}