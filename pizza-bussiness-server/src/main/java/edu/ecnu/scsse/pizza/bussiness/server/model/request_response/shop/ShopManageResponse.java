package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Shop;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;

import java.util.List;

public class ShopManageResponse extends BaseResponse{
    private List<Shop> factoryList;

    public ShopManageResponse(){

    }

    public ShopManageResponse(List<Shop> factoryList) {
        this.factoryList = factoryList;
    }

    public ShopManageResponse(BusinessServerException e) {
        super(e);
    }

    public List<Shop> getFactoryList() {
        return factoryList;
    }

    public void setFactoryList(List<Shop> factoryList) {
        this.factoryList = factoryList;
    }
}
