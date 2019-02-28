package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.ShopIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopIngredientJpaRepository extends JpaRepository<ShopIngredientEntity,Long> {

}