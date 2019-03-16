package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuJpaRepository extends JpaRepository<MenuEntity,Integer> {
    Optional<MenuEntity> findById(int id);
}