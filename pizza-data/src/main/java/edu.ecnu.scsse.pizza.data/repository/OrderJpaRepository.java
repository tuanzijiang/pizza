package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<OrderEntity,Integer> {

    Optional<OrderEntity> findByOrderUuid(String orderId);

    @Query(value="select *\n" +
            "    from pizza_order\n" +
            "    where DATEDIFF(DATE(commit_time),?1)=0",nativeQuery = true)
    List<OrderEntity> findOrderByCommitTime(String date);
}