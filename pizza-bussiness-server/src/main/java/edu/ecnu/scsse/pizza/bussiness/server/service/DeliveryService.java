package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Driver;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.PriorityQueue;

@Service
public class DeliveryService {

    private static int maxDeliveryNumEachDriver = 8;
    private List<Driver> driverList;
    private PriorityQueue<Order> waitToDeliveryOrderQueue;

    public void deliveryOrder(OrderEntity orderEntity){
        String orderUuid = orderEntity.getOrderUuid();
        int shopId = orderEntity.getShopId();
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }
}
