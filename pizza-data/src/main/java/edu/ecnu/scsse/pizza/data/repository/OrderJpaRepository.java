package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<OrderEntity,Integer> {

    Optional<OrderEntity> findByOrderUuid(String orderId);

    @Query("select id from OrderEntity where orderUuid=?1")
    Optional<Integer> findIdByOrderUuid(String orderId);

    @Query("select id from OrderEntity where orderUuid=?1 and state=1")
    Optional<Integer> findCartIdByOrderUuid(String orderId);

    Optional<OrderEntity> findFirstByUserIdAndStateOrderByIdDesc(Integer userId, Integer state);

    List<OrderEntity> findByUserIdAndStateInAndIdGreaterThan(Integer userId,
                                                               List<Integer> states,
                                                               Integer lastOrderId,
                                                               Pageable pageable);


    // update
    @Query("update OrderEntity set state=?1 where orderUuid=?2")
    int updateStateByOrderUuid(Integer state, String orderUuid);

}