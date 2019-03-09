package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.model.OrderReceiveRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.OrderReceiveResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.MapPoint;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Point;
import edu.ecnu.scsse.pizza.data.domain.AddressEntity;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import edu.ecnu.scsse.pizza.data.repository.AddressJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.UserAddressJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderReceiveService {
    private static final Logger log = LoggerFactory.getLogger(OrderReceiveService.class);

    @Autowired
    private PizzaShopJpaRepository pizzaShopJpaRepository;
    @Autowired
    private UserAddressJpaRepository userAddressJpaRepository;
    @Autowired
    private AddressJpaRepository addressJpaRepository;

    public OrderReceiveResponse getReceiveShopId(OrderReceiveRequest orderReceiveRequest)
    {
        int orderUuid = orderReceiveRequest.getOrderUuid();
        int addressUserId = orderReceiveRequest.getUserAddressId();
        Optional<UserAddressEntity> userAddressEntity = userAddressJpaRepository.findById(addressUserId);
        List<PizzaShopEntity> pizzaShopEntityList = pizzaShopJpaRepository.findAll();


        OrderReceiveResponse orderReceiveResponse=new OrderReceiveResponse();
        if (userAddressEntity.isPresent()){
            orderReceiveResponse =new OrderReceiveResponse();
            UserAddressEntity userAddress = userAddressEntity.get();
            int addressId = userAddress.getAddressId();
            Optional<AddressEntity> addressEntity = addressJpaRepository.findById(addressId);
            if (addressEntity.isPresent()){
                AddressEntity address = addressEntity.get();
                double lat = address.getLat().doubleValue();
                double lon = address.getLon().doubleValue();
                Point destination = new MapPoint(lat,lon);
            }
        }
        return orderReceiveResponse;
    }

}
