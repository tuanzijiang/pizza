package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.ExceptionType;
import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Driver;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateObject;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateResult;
import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CastEntity;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.bean.DriverBean;
import edu.ecnu.scsse.pizza.data.domain.DriverEntity;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import edu.ecnu.scsse.pizza.data.enums.DriverStatus;
import edu.ecnu.scsse.pizza.data.repository.DriverJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.OrderJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverService {
    private static final Logger log = LoggerFactory.getLogger(ShopService.class);

    @Autowired
    private DriverJpaRepository driverJpaRepository;

    @Autowired
    private PizzaShopJpaRepository shopJpaRepository;

    @Autowired
    private OperateLoggerService operateLoggerService;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    public List<DriverManageResponse> getDriverList() throws Exception{
        List<DriverManageResponse> driverList = new ArrayList<>();
        List<Object[]> objects = driverJpaRepository.findAllDrivers();
        List<DriverBean> driverBeans = CastEntity.castEntityToDriverBean(objects,DriverBean.class);
        if(driverBeans.size()!=0){
            driverList = driverBeans.stream().map(this::convert).collect(Collectors.toList());
        }
        else{
            NotFoundException e = new NotFoundException("Driver list is not found.");
            log.warn("Fail to find the driver list.", e);
        }

        return driverList;
    }

    public DriverDetailResponse editDriverDetail(DriverDetailRequest request, int adminId) throws BusinessServerException{
        DriverDetailResponse driverDetailResponse;
        int driverId = request.getId();
        String operateType = OperateType.UPDATE.getExpression();
        String operateObj = OperateObject.DRIVER.getExpression() + String.valueOf(driverId);
        try {
            Optional<DriverEntity> optional = driverJpaRepository.findById(driverId);
            if (optional.isPresent()) {
                DriverEntity entity = optional.get();
                entity.setName(request.getName());
                entity.setPhone(request.getPhone());
                entity.setShopId(request.getShopId());
                driverJpaRepository.saveAndFlush(entity);
                driverDetailResponse = new DriverDetailResponse(driverId);
                operateLoggerService.addOperateLogger(adminId, operateType, operateObj, OperateResult.SUCCESS.getExpression());
            } else {
                String message = String.format("Driver %s is not found.",driverId);
                NotFoundException e = new NotFoundException(message);
                driverDetailResponse = new DriverDetailResponse(e);
                log.warn(message, e);
                operateLoggerService.addOperateLogger(adminId, operateType, operateObj, OperateResult.FAILURE.getExpression() + " :"+message);
            }
        }catch (Exception e){
            log.error("Fail to update driver.",e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, "Fail to update driver.", e);
        }

        return driverDetailResponse;
    }

    public DriverDetailResponse addNewDriver(DriverDetailRequest request,int adminId) throws BusinessServerException{
        DriverEntity driverEntity;
        DriverDetailResponse response;
        String type = OperateType.INSERT.getExpression();//操作类型
        String object = OperateObject.DRIVER.getExpression();//操作对象
        try {
            driverEntity = new DriverEntity();
            driverEntity.setName(request.getName());
            driverEntity.setPhone(request.getPhone());
            driverEntity.setShopId(request.getShopId());
            driverJpaRepository.saveAndFlush(driverEntity);
            response = new DriverDetailResponse(driverEntity.getId());
            operateLoggerService.addOperateLogger(adminId, type, object, OperateResult.SUCCESS.getExpression());
        }catch (Exception e){
            String message = "Fail to insert driver.";
            log.error(message,e);
            throw new BusinessServerException(ExceptionType.REPOSITORY, message, e);
        }
        return response;
    }

    @Transactional
    public SimpleResponse deleteDriver(int driverId, int adminId){
        SimpleResponse response = new SimpleResponse();
        String type = OperateType.DELETE.getExpression();
        String object = OperateObject.DRIVER.getExpression()+driverId;
        try {
            driverJpaRepository.deleteById(driverId);
            orderJpaRepository.updateOrdersDriver(driverId);
            operateLoggerService.addOperateLogger(adminId,type, object, OperateResult.SUCCESS.getExpression());
            response.setResultType(ResultType.SUCCESS);
            response.setSuccessMsg("删除成功");
        }catch (Exception e){
            operateLoggerService.addOperateLogger(adminId,type,object,OperateResult.FAILURE.getExpression());
            response.setResultType(ResultType.FAILURE);
            response.setErrorMsg(e.getMessage());
        }
        return response;
    }

    private DriverManageResponse convert(DriverBean driverBean){
        DriverManageResponse driver = new DriverManageResponse();
        CopyUtils.copyProperties(driverBean,driver);
        driver.setState(DriverStatus.fromDbValue(driverBean.getState()).getExpression());
        return driver;
    }
}
