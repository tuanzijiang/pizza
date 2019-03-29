package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

public class ShopDetailResponse extends BaseResponse{
    private int shopId;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public ShopDetailResponse() {
    }

    public ShopDetailResponse(BusinessServerException e) {
        super(e);
    }
}
