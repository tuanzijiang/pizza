package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopManageResponse;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService{
    private static final Logger log = LoggerFactory.getLogger(ShopService.class);

    @Autowired
    PizzaShopJpaRepository shopJpaRepository;

    public ShopManageResponse getShopList(){
        ShopManageResponse shopManageResponse;
        List<PizzaShopEntity> shopEntityList = shopJpaRepository.findAll();
        if(shopEntityList.size()!=0){
            shopManageResponse = new ShopManageResponse();
            //List<Shop> shopList = shopEntityList.stream().map(this::convert).collect(Collectors.toList());
            shopManageResponse.setFactoryList(shopEntityList);
        }
        else{
            NotFoundException e = new NotFoundException("Shop list is not found.");
            shopManageResponse = new ShopManageResponse(e);
            log.warn("Fail to find the shop list.", e);
        }

        return shopManageResponse;
    }


}
