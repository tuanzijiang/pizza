package edu.ecnu.scsse.pizza.consumer.server.model.order;

import lombok.Data;

@Data
public class SendOrderRequest {
    private String orderId;
}
