package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IngredientJpaRepository extends JpaRepository<IngredientEntity,Integer> {
    @Query("select alermNum from IngredientEntity where id=?1")
    int findAlarmNumById(int id);

    @Query(value = "select count from ingredient where id=?1",nativeQuery = true)
    int findCountById(int id);

    @Transactional
    @Modifying
    @Query(value="update ingredient set ingredient.count=?1 where id=?2",nativeQuery = true)
    int updateCountByIngredientId(int count, int ingredientId);
}