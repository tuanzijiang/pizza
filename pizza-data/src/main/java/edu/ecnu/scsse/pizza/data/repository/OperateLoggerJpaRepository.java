package edu.ecnu.scsse.pizza.data.repository;

import edu.ecnu.scsse.pizza.data.domain.OperateLoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperateLoggerJpaRepository extends JpaRepository<OperateLoggerEntity,Integer> {
    @Query(value = "select * from operate_logger",nativeQuery = true)
    List<OperateLoggerEntity> findAllLoggers();
}