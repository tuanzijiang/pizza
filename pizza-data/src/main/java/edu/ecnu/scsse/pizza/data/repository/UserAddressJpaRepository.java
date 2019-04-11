package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.bean.UserAddressBean;
import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserAddressJpaRepository extends JpaRepository<UserAddressEntity,Integer> {

    Optional<UserAddressEntity> findByUserIdAndAddressId(Integer userId, Integer addressId);

    List<UserAddressEntity> findByUserId(Integer userId);

    @Query(value = "select new edu.ecnu.scsse.pizza.data.bean.UserAddressBean (ua.id, ua.name, ua.sex, a.address, ua.addressDetail, ua.phone) "
            + "from AddressEntity a, UserAddressEntity ua where a.id = ua.addressId and"
            + " ua.userId = :userId and ua.addressId = :addressId")
    Optional<UserAddressBean> findUserAddressBeanByUserIdAndAddressId(@Param("userId") Integer userId, @Param("addressId") Integer addressId);

    @Transactional
    @Modifying
    @Query("update UserAddressEntity " +
            "set phone=:#{#userAddress.phone}, name=:#{#userAddress.name}, " +
            "addressDetail=:#{#userAddress.addressDetail}, sex=:#{#userAddress.sex}, tag=:#{#userAddress.tag} " +
            "where userId=:#{#userAddress.userId} and addressId=:#{#userAddress.addressId}")
    int updateByUserIdAndAddressId(@Param("userAddress") UserAddressEntity userAddress);
}