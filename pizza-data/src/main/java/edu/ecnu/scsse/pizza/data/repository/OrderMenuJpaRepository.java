package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.OrderMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMenuJpaRepository extends JpaRepository<OrderMenuEntity,Integer> {
    List<OrderMenuEntity> findByOrderId(int orderId);
}