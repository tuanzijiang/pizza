package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverJpaRepository extends JpaRepository<DriverEntity,Long> {

}