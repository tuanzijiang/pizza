package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.FakeAdmin;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Logger;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateType;
import edu.ecnu.scsse.pizza.data.domain.OperateLoggerEntity;
import edu.ecnu.scsse.pizza.data.repository.OperateLoggerJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OperateLoggerServiceTest extends TestApplication{
    @Mock
    private OperateLoggerJpaRepository operateLoggerJpaRepository;

    @InjectMocks
    private OperateLoggerService operateLoggerService;

    @Before
    public void setUp(){

    }

    @Test
    public void testAddOperateLogger(){
        int adminId = 1;
        String type = OperateType.INSERT.getExpression();
        String object = "obj";
        String result = "success";
        OperateLoggerEntity entity = FakeAdmin.fakeOperateLogger(adminId,type,object,result);
        when(operateLoggerJpaRepository.save(entity)).thenReturn(entity);
        operateLoggerService.addOperateLogger(adminId,type,object,result);
        verify(operateLoggerJpaRepository).save(isA(OperateLoggerEntity.class));
    }

    @Test
    public void testGetLoggerList(){
        List<OperateLoggerEntity> loggerEntities = FakeAdmin.fakeLoggers();

        when(operateLoggerJpaRepository.findAll()).thenReturn(loggerEntities);
        List<Logger> loggers = operateLoggerService.getOperateLogger();
        assertEquals(loggerEntities.size(),loggers.size());
    }
}
