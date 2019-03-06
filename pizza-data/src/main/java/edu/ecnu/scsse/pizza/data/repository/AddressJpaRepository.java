package edu.ecnu.scsse.pizza.data.repository;


import edu.ecnu.scsse.pizza.data.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<AddressEntity,Integer> {
}