package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAddressJpaRepository extends JpaRepository<UserAddressEntity,Integer> {
    Optional<UserAddressEntity> findByUserIdAndAddressId(Integer userId, Integer addressId);
}