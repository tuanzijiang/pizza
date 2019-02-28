package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressJpaRepository extends JpaRepository<UserAddressEntity,Long> {

}