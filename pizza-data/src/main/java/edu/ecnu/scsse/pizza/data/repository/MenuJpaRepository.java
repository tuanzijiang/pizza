package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.bean.PizzaBean;
import edu.ecnu.scsse.pizza.data.domain.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

import java.util.Optional;

public interface MenuJpaRepository extends JpaRepository<MenuEntity,Integer> {
    List<MenuEntity> findAllByState(Integer state);

    List<MenuEntity> findAllByStateAndIdIn(Integer state, Collection<Integer> idList);

    @Query("select new edu.ecnu.scsse.pizza.data.bean.PizzaBean(m.id, m.name, m.description, m.price, m.tag, m.image, m.state, om.count) from MenuEntity m, OrderMenuEntity om " +
            "where m.id = om.menuId and om.orderId = :orderId and m.state = :state")
    List<PizzaBean> findPizzaBeansByStateAndOrderId(@Param("state") Integer state, @Param("orderId") Integer orderId);

    @Query("select new edu.ecnu.scsse.pizza.data.bean.PizzaBean(m.id, m.name, m.description, m.price, m.tag, m.image, m.state, om.count) from MenuEntity m, OrderMenuEntity om " +
            "where m.id = om.menuId and om.orderId = :orderId")
    List<PizzaBean> findPizzaBeansByOrderId(@Param("orderId") Integer orderId);

    @Query("select new edu.ecnu.scsse.pizza.data.bean.PizzaBean(m.id, m.name, m.description, m.price, m.tag, m.image, m.state, om.count) from MenuEntity m, OrderMenuEntity om " +
            "where m.id = om.menuId and om.orderId in :orderIds")
    List<PizzaBean> findPizzaBeansByOrderIds(@Param("orderIds") List<Integer> orderIds);

    Optional<MenuEntity> findById(int id);

    @Query(value = "select * from menu",nativeQuery = true)
    List<MenuEntity> findAllMenuEntities();
}