package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    public void deliveryOrder(OrderEntity orderEntity){
        String orderUuid = orderEntity.getOrderUuid();
        int shopId = orderEntity.getShopId();
    }
}
