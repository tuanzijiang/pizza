package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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

    @Query(value="select count(*)\n" +
            "    from pizza_order\n" +
            "    where DATEDIFF(DATE(commit_time),?1)=0",nativeQuery = true)
    int findTotalOrdersByCommitTime(String date);

    @Query(value="select count(*)\n" +
            "    from pizza_order\n" +
            "    where DATEDIFF(DATE(commit_time),?1)=0 and pizza_order.state = ?2",nativeQuery = true)
    int findTotalOrdersByCommitTimeAndState(String date,int state);

    @Query(value="select ifnull(sum(total_price),0)\n" +
            "    from pizza_order\n" +
            "    where DATEDIFF(DATE(commit_time),?1)=0",nativeQuery = true)
    double findTotalAmountByCommitTime(String date);

    // update
    @Transactional
    @Modifying
    @Query("update OrderEntity set state=?1 where orderUuid=?2")
    int updateStateByOrderUuid(Integer state, String orderUuid);

    // update
    @Transactional
    @Modifying
    @Query("update OrderEntity set state=?1, totalPrice=?2 where orderUuid=?3")
    int updateStateAndTotalPriceByOrderUuid(Integer state, Double totalPrice, String orderUuid);

    @Query(value="select * from pizza_order where DATEDIFF(commit_time,NOW()) = 0",nativeQuery = true)
    List<OrderEntity> findAllOrderCommitToday();

    @Query(value="select * from pizza_order where shop_id=?1 and DATEDIFF(commit_time,NOW()) = 0",nativeQuery = true)
    List<OrderEntity> findOrderCommitTodayByShopId(int shopId);

    @Transactional
    @Modifying
    @Query(value="update pizza_order set state=?1 , shop_id=?2 where order_uuid=?3",nativeQuery = true)
    int updateStateAndShopIdByOrderUuid(int state,int shopId,String orderUuid);

    @Query(value = "select * from pizza_order where state=4", nativeQuery = true)
    List<OrderEntity> findPendingList();

    @Query(value = "select * from pizza_order where state=5 or state=6", nativeQuery = true)
    List<OrderEntity> findCancelOrderList();

    @Query(value = "select * from pizza_order where driver_id=?1 and state=?2",nativeQuery = true)
    List<OrderEntity> findWaitToDeliveryOrderByDriverId(int driverId,int waitToDeliveryOrderState);

    @Transactional
    @Modifying
    @Query(value = "update pizza_order set state=?1,deliver_start_time=?2 where id=?3",nativeQuery = true)
    int updateStateAndStartDeliveryTimeByOrderId(int state,Timestamp deliveryStartTime,int id);

    @Transactional
    @Modifying
    @Query(value = "update pizza_order set state=?1,deliver_end_time=?2 where id=?3",nativeQuery = true)
    int updateStateAndEndDeliveryTimeByOrderId(int state,Timestamp deliveryEndTime,int id);

    @Transactional
    @Modifying
    @Query(value = "update pizza_order set state=?1, driver_id=?2 where id=?3",nativeQuery = true)
    int updateStateAndDriverIdByOrderId(int state,int driverId,int orderId);

    @Transactional
    @Modifying
    @Query(value = "update pizza_order set commit_time=?1 where id=?2",nativeQuery = true)
    int updateCommitTime(Timestamp commitTime,int orderId);

    @Query(value = "select * from pizza_order", nativeQuery = true)
    List<OrderEntity> findAllOrders();

    @Query(value = "select o.id,o.address_id as addressId,o.state,o.total_price as totalAmount,\n" +
            "o.driver_id as driverId, o.shop_id as shopId, o.commit_time as commitTime,o.deliver_start_time as startDeliverTime, o.deliver_end_time as arriveTime, o.order_uuid as orderUuid,\n" +
            "order_detail.shop_name as shopName,order_detail.buy_phone as buyPhone,\n" +
            "order_detail.receive_name as receiveName,order_detail.receive_phone as receivePhone,order_detail.receive_address as receiveAddress,\n" +
            "order_detail.driver_name as driverName,order_detail.driver_phone as driverPhone\n" +
                    "from pizza_order as o natural join\n" +
                    "(SELECT shop_user.id,shop_user.shop_name,shop_user.buy_phone,\n" +
                    "address_driver.receive_name,address_driver.receive_phone,address_driver.receive_address,\n" +
                    "address_driver.driver_name,address_driver.driver_phone\n" +
                    "FROM\n" +
                    "(select order_shop.id,order_shop.shop_name,order_user.buy_phone\n" +
                    "from\n" +
                    "(select o.id,s.name as shop_name\n" +
                    "from pizza_order as o left join pizza_shop as s\n" +
                    "on o.shop_id = s.id) as order_shop\n" +
                    "LEFT JOIN\n" +
                    "(select o.id,u.phone as buy_phone\n" +
                    "from pizza_order as o left join user as u\n" +
                    "on o.user_id = u.id) as order_user\n" +
                    "on order_shop.id = order_user.id\n" +
                    ") as shop_user\n" +
                    "NATURAL JOIN\n" +
                    "(select order_address.id, order_address.receive_name, order_address.receive_phone, order_address.receive_address,\n" +
                    "order_driver.driver_name,order_driver.driver_phone\n" +
                    "from (select o.id,ua.name as receive_name, ua.phone as receive_phone, ua.address_detail as receive_address \n" +
                    "from pizza_order as o left join user_address as ua\n" +
                    "on o.address_id = ua.address_id\n" +
                    ") as order_address\n" +
                    "left join\n" +
                    "(select o.id,driver.id as driver_id, driver.name as driver_name, driver.phone as driver_phone\n" +
                    "from pizza_order as o left join driver\n" +
                    "on o.driver_id = driver.id\n" +
                    ") as order_driver\n" +
                    "on order_address.id = order_driver.id\n" +
                    ") as address_driver\n" +
                    ") as order_detail\n" +
                    "where o.state<>1\n" +
                    "order by o.id asc",
            nativeQuery = true)
    List<Object[]> getOrderBeans();

    @Query(value = "select o.id,o.address_id as addressId,o.state,o.total_price as totalAmount,\n" +
            "o.driver_id as driverId, o.shop_id as shopId, o.commit_time as commitTime,o.deliver_start_time as startDeliverTime, o.deliver_end_time as arriveTime, o.order_uuid as orderUuid,\n" +
            "order_detail.shop_name as shopName,order_detail.buy_phone as buyPhone,\n" +
            "order_detail.receive_name as receiveName,order_detail.receive_phone as receivePhone,order_detail.receive_address as receiveAddress,\n" +
            "order_detail.driver_name as driverName,order_detail.driver_phone as driverPhone\n" +
            "from pizza_order as o natural join\n" +
            "(SELECT shop_user.id,shop_user.shop_name,shop_user.buy_phone,\n" +
            "address_driver.receive_name,address_driver.receive_phone,address_driver.receive_address,\n" +
            "address_driver.driver_name,address_driver.driver_phone\n" +
            "FROM\n" +
            "(select order_shop.id,order_shop.shop_name,order_user.buy_phone\n" +
            "from\n" +
            "(select o.id,s.name as shop_name\n" +
            "from pizza_order as o left join pizza_shop as s\n" +
            "on o.shop_id = s.id) as order_shop\n" +
            "LEFT JOIN\n" +
            "(select o.id,u.phone as buy_phone\n" +
            "from pizza_order as o left join user as u\n" +
            "on o.user_id = u.id) as order_user\n" +
            "on order_shop.id = order_user.id\n" +
            ") as shop_user\n" +
            "NATURAL JOIN\n" +
            "(select order_address.id, order_address.receive_name, order_address.receive_phone, order_address.receive_address,\n" +
            "order_driver.driver_name,order_driver.driver_phone\n" +
            "from (select o.id,ua.name as receive_name, ua.phone as receive_phone, ua.address_detail as receive_address \n" +
            "from pizza_order as o left join user_address as ua\n" +
            "on o.address_id = ua.address_id\n" +
            ") as order_address\n" +
            "left join\n" +
            "(select o.id,driver.id as driver_id, driver.name as driver_name, driver.phone as driver_phone\n" +
            "from pizza_order as o left join driver\n" +
            "on o.driver_id = driver.id\n" +
            ") as order_driver\n" +
            "on order_address.id = order_driver.id\n" +
            ") as address_driver\n" +
            ") as order_detail\n" +
            "where o.id = ?1",nativeQuery = true
    )
    List<Object[]> getOrderBeanById(int orderId);

    @Transactional
    @Modifying
    @Query(value = "update pizza_order\n" +
            "set driver_id = null \n" +
            "where driver_id = ?1", nativeQuery = true)
    int updateOrdersDriver(int driverId);

    @Query(value = "select sale.date,orderNum,ifnull(completeNum,0),ifnull(cancelNum,0),totalAmount\n" +
            "from\n" +
            "(select total.date,orderNum,completeNum,totalAmount\n" +
            "from\n" +
            "(select DATE(commit_time) as date, count(*) as orderNum,ifnull(sum(total_price),0) as totalAmount\n" +
            "from pizza_order\n" +
            "where DATE(commit_time) is not null\n" +
            "group by date) as total\n" +
            "left join \n" +
            "(select DATE(commit_time) as date, count(*) as completeNum\n" +
            "from pizza_order\n" +
            "where DATE(commit_time) is not null and state = 9\n" +
            "group by date) as complete\n" +
            "on total.date = complete.date) as sale\n" +
            "left join\n" +
            "(select DATE(commit_time) as date, count(*) as cancelNum\n" +
            "from pizza_order\n" +
            "where DATE(commit_time) is not null and state = 5\n" +
            "group by date) as cancel\n" +
            "on sale.date = cancel.date\n" +
            "order by sale.date asc",nativeQuery = true)
    List<Object[]> getSaleStatus();
}

