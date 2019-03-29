package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.data.domain.OperateLoggerEntity;
import edu.ecnu.scsse.pizza.data.repository.OperateLoggerJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;

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
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = df.format(date);
        Timestamp ts = Timestamp.valueOf(dateStr);
        logger.setOperateTime(new Timestamp(ts.getTime()));
        logger.setAdminId(getAdminId());
        logger.setOperateDetail(type+object+result);
        operateLoggerJpaRepository.save(logger);
    }
}
