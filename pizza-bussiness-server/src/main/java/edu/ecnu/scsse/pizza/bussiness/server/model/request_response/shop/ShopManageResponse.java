package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;

import java.util.List;

public class ShopManageResponse extends BaseResponse{
    private List<PizzaShopEntity> factoryList;

    public ShopManageResponse(){

    }

    public ShopManageResponse(List<PizzaShopEntity> factoryList) {
        this.factoryList = factoryList;
    }

    public ShopManageResponse(BusinessServerException e) {
        super(e);
    }

    public List<PizzaShopEntity> getFactoryList() {
        return factoryList;
    }

    public void setFactoryList(List<PizzaShopEntity> factoryList) {
        this.factoryList = factoryList;
    }
}
