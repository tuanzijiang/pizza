package edu.ecnu.scsse.pizza.consumer.server.model.order;

import lombok.Data;

@Data
public class CancelOrderRequest {
    private String orderId;
}
