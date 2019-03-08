package edu.ecnu.scsse.pizza.consumer.server.model.order;

import lombok.Data;

@Data
public class FetchOrderRequest {

    private Integer userId;

    private String orderId;

}
