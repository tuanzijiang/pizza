package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaShopJpaRepository extends JpaRepository<PizzaShopEntity,Integer> {
    Optional<PizzaShopEntity> findPizzaShopEntityById(Integer id);
}