package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

import java.util.Optional;

public interface MenuJpaRepository extends JpaRepository<MenuEntity,Integer> {
    List<MenuEntity> findAllByState(Integer state);

    List<MenuEntity> findAllByStateAndIdIn(Integer state, Collection<Integer> idList);

    Optional<MenuEntity> findById(int id);

    @Query(value = "select * from menu",nativeQuery = true)
    List<MenuEntity> findAllMenuEntities();
}