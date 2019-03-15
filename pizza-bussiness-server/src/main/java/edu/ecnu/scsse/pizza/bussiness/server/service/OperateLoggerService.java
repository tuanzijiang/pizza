package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.data.domain.OperateLoggerEntity;
import edu.ecnu.scsse.pizza.data.repository.OperateLoggerJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Clock;

@Service
public class OperateLoggerService extends SessionService{
    @Autowired
    OperateLoggerJpaRepository operateLoggerJpaRepository;

    /**
     * 添加管理员操作日志
     * */
    void addOperateLogger(String type, String object, String result){
        OperateLoggerEntity logger = new OperateLoggerEntity();
        logger.setOperateType(type);
        Clock clock = Clock.systemDefaultZone();
        logger.setOperateTime(new Timestamp(clock.millis()));
        logger.setAdminId(getAdminId());
        logger.setOperateDetail(type+object+result);
        operateLoggerJpaRepository.save(logger);
    }
}
