package edu.ecnu.scsse.pizza.bussiness.server.utils;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Driver;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.MapPoint;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Shop;
import edu.ecnu.scsse.pizza.bussiness.server.service.DeliveryService;
import edu.ecnu.scsse.pizza.bussiness.server.service.OrderReceiveService;
import edu.ecnu.scsse.pizza.data.domain.DriverEntity;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import edu.ecnu.scsse.pizza.data.domain.ShopIngredientEntity;
import edu.ecnu.scsse.pizza.data.repository.DriverJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.OrderJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.ShopIngredientJpaRepository;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(value = 0)
public class InitSystemConfig implements CommandLineRunner{

    @Autowired
    private PizzaShopJpaRepository pizzaShopJpaRepository;
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
        for(DriverEntity driverEntity:driverEntityList){
            Driver driver = new Driver();
        }
    }
}
