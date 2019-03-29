package edu.ecnu.scsse.pizza.bussiness.server.utils;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.*;
import edu.ecnu.scsse.pizza.bussiness.server.service.DeliveryService;
import edu.ecnu.scsse.pizza.bussiness.server.service.OrderReceiveService;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.DriverStatus;
import edu.ecnu.scsse.pizza.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
@Order(value = 0)
public class InitSystemConfig implements CommandLineRunner{

    @Autowired
    private PizzaShopJpaRepository pizzaShopJpaRepository;
    @Autowired
    private AddressJpaRepository addressJpaRepository;
    @Autowired
    private OrderJpaRepository orderJpaRepository;
    @Autowired
    private OrderReceiveService orderReceiveService;
    @Autowired
    private DriverJpaRepository driverJpaRepository;
    @Autowired
    private DeliveryService deliveryService;

    @Override
    public void run(String... args) throws Exception{
        initOrderReceiveShopOrderNumMap();
        initDriverListinDeliveryService();
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    private void initOrderReceiveShopOrderNumMap(){
        Map<Integer,Integer> shopOrderNumMap= new HashMap<>();
        List<PizzaShopEntity> pizzaShopEntityList = pizzaShopJpaRepository.findAll();
        for(PizzaShopEntity pizzaShopEntity:pizzaShopEntityList){
            shopOrderNumMap.put(pizzaShopEntity.getId(),0);
        }
        List<OrderEntity> orderEntityList = orderJpaRepository.findAllOrderCommitToday();
        for(OrderEntity orderEntity:orderEntityList){
            if (orderEntity.getShopId() != null){
                shopOrderNumMap.put(orderEntity.getShopId(),shopOrderNumMap.get(orderEntity.getShopId())+1);
            }
        }
        orderReceiveService.setShopOrderNumMap(shopOrderNumMap);
    }

    private void initDriverListinDeliveryService(){
        List<DriverEntity> driverEntityList = driverJpaRepository.findAll();
        List<Driver> driverList = new ArrayList<>();
        Map<Integer,Timer> driverTimerMap = new HashMap<>();
        for(DriverEntity driverEntity:driverEntityList){

            //装配一下driver的信息
            Driver driver = new Driver();
            driver.setId(driverEntity.getId());
            driver.setShopId(driverEntity.getShopId());
            driver.setState(DriverStatus.fromDbValue(driverEntity.getState()));
            List<edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order> orderList=new ArrayList<>();
            driver.setOrderList(orderList);
            driverList.add(driver);
            Timer timer=new Timer();
            driverTimerMap.put(driver.getId(),timer);
        }
        deliveryService.setDriverList(driverList);
        deliveryService.setScheduledThreadPoolExecutor();
        deliveryService.setDriverTimerMap(driverTimerMap);
        PriorityQueue<edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order> priorityQueue=new PriorityQueue<>();
        deliveryService.setWaitToDeliveryOrderQueue(priorityQueue);
    }
}
