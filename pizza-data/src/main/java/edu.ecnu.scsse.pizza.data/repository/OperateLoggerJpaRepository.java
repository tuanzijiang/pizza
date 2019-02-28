package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.OperateLoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperateLoggerJpaRepository extends JpaRepository<OperateLoggerEntity,Long> {

}