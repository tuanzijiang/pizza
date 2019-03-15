package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MenuJpaRepository extends JpaRepository<MenuEntity,Integer> {
    List<MenuEntity> findAllByState(Integer state);

    List<MenuEntity> findAllByStateAndIdIn(Integer state, Collection<Integer> idList);
}