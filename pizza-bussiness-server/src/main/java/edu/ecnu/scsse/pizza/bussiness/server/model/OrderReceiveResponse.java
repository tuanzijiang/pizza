package edu.ecnu.scsse.pizza.bussiness.server.model;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;

public class OrderReceiveResponse extends BaseResponse{
    private int shopId;

    public OrderReceiveResponse() {
    }

    public OrderReceiveResponse(int shopId) {
        this.shopId = shopId;
    }

    public OrderReceiveResponse(BusinessServerException e, int shopId) {
        super(e);
        this.shopId = -1;
    }

    public OrderReceiveResponse(BusinessServerException e) {
        super(e);
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
