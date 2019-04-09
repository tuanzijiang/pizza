package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DriverJpaRepository extends JpaRepository<DriverEntity,Integer> {
    @Query(value = "select * from driver where shop_id=?1",nativeQuery = true)
    List<DriverEntity> findAllDriverByShopId(int shopId);

    @Transactional
    @Modifying
    @Query(value = "update driver set state=?1 where id=?2",nativeQuery = true)
    int updateDriverStateById(int state,int driverId);

    @Query(value = "select * from driver where id=?1 for update",nativeQuery = true)
    List<DriverEntity> findDriverByIdForUpdate(int driverId);

    @Query(value = "select driver.id,driver.name,driver.phone,driver.state,shop_id as shopId,pizza_shop.`name` as shopName\n" +
            "from driver LEFT JOIN pizza_shop\n" +
            "on driver.shop_id = pizza_shop.id",nativeQuery = true)
    List<Object[]> findAllDrivers();

}