package edu.ecnu.scsse.pizza.data.repository;

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

    List<UserAddressEntity> findByUserIdAndAddressIdIn(Integer userId, List<Integer> addressId);

    List<UserAddressEntity> findByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query("update UserAddressEntity " +
            "set phone=:#{#userAddress.phone}, name=:#{#userAddress.name}, " +
            "addressDetail=:#{#userAddress.addressDetail}, sex=:#{#userAddress.sex}, tag=:#{#userAddress.tag} " +
            "where userId=:#{#userAddress.userId} and addressId=:#{#userAddress.addressId}")
    int updateByUserIdAndAddressId(@Param("userAddress") UserAddressEntity userAddress);
}