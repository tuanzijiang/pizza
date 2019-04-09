package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PizzaShopJpaRepository extends JpaRepository<PizzaShopEntity,Integer> {
    Optional<PizzaShopEntity> findPizzaShopEntityById(Integer id);

    @Query(value = "select * from pizza_shop",nativeQuery = true)
    List<PizzaShopEntity> findAllShops();
}