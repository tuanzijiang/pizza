package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.Transactional;
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

    @Query(value="select *\n" +
            "    from pizza_order\n" +
            "    where DATEDIFF(DATE(commit_time),?1)=0",nativeQuery = true)
    List<OrderEntity> findOrderByCommitTime(String date);

    // update
    @Transactional
    @Modifying
    @Query("update OrderEntity set state=?1 where orderUuid=?2")
    int updateStateByOrderUuid(Integer state, String orderUuid);

    @Query(value="select * from pizza_order where DATEDIFF(commit_time,NOW()) = 0",nativeQuery = true)
    List<OrderEntity> findAllOrderCommitToday();

    @Query(value="select * from pizza_order where shop_id=?1 and DATEDIFF(commit_time,NOW()) = 0",nativeQuery = true)
    List<OrderEntity> findOrderCommitTodayByShopId(int shopId);

    @Transactional
    @Modifying
    @Query(value="update pizza_order set state=?1 , shop_id=?2 where order_uuid=?3",nativeQuery = true)
    int updateStateAndShopIdByOrderUuid(int state,int shopId,String orderUuid);
}