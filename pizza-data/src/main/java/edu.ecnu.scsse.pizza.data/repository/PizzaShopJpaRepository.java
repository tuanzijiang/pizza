package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaShopJpaRepository extends JpaRepository<PizzaShopEntity,Integer> {

}