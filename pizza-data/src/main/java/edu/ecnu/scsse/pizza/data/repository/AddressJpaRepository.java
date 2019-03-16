package edu.ecnu.scsse.pizza.data.repository;


import edu.ecnu.scsse.pizza.data.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressJpaRepository extends JpaRepository<AddressEntity,Integer> {
    Optional<AddressEntity> findById(int id);
}