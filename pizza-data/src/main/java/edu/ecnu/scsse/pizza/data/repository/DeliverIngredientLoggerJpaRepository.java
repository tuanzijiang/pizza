package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.DeliverIngredientLoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliverIngredientLoggerJpaRepository extends JpaRepository<DeliverIngredientLoggerEntity,Integer> {

}