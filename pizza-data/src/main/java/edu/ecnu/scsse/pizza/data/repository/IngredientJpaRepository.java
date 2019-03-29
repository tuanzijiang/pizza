package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientJpaRepository extends JpaRepository<IngredientEntity,Integer> {
    @Query("select alermNum from IngredientEntity where id=?1")
    int findAlarmNumById(int id);
}