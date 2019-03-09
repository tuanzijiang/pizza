package edu.ecnu.scsse.pizza.bussiness.server.model;

public class OrderReceiveRequest {
    private int orderUuid;
    private int userAddressId;

    public int getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(int orderUuid) {
        this.orderUuid = orderUuid;
    }

    public int getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(int userAddressId) {
        this.userAddressId = userAddressId;
    }

    public OrderReceiveRequest(int orderUuid, int userAddressId) {
        this.orderUuid = orderUuid;
        this.userAddressId = userAddressId;
    }
}
