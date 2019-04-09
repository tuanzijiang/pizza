package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Logger;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.OperateLoggerEntity;
import edu.ecnu.scsse.pizza.data.repository.OperateLoggerJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperateLoggerService extends SessionService{
    @Autowired
    OperateLoggerJpaRepository operateLoggerJpaRepository;

    /**
     * 添加管理员操作日志
     * */
    void addOperateLogger(int adminId, String type, String object, String result){
        OperateLoggerEntity logger = new OperateLoggerEntity();
        logger.setOperateType(type);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = df.format(date);
        Timestamp ts = Timestamp.valueOf(dateStr);
        logger.setOperateTime(new Timestamp(ts.getTime()));
        logger.setAdminId(adminId);
        logger.setOperateDetail(type+object+result);
        operateLoggerJpaRepository.save(logger);
    }

    public List<Logger> getOperateLogger(){
        List<OperateLoggerEntity> operateLoggerEntityList = operateLoggerJpaRepository.findAll();
        return operateLoggerEntityList.stream().map(this::convert).collect(Collectors.toList());
    }

    private Logger convert(OperateLoggerEntity entity){
        Logger logger = new Logger();
        CopyUtils.copyProperties(entity,logger);

        String commitTimePattern = "yyyy/MM/dd hh:MM:ss";
        DateFormat df = new SimpleDateFormat(commitTimePattern);
        logger.setOperateTime(df.format(entity.getOperateTime()));
        return logger;
    }
}
