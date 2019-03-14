package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order;

public class OrderReceiveRequest {
    private String orderUuid;
    private int userAddressId;

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public int getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(int userAddressId) {
        this.userAddressId = userAddressId;
    }

    public OrderReceiveRequest(String orderUuid, int userAddressId) {
        this.orderUuid = orderUuid;
        this.userAddressId = userAddressId;
    }
}
