package edu.ecnu.scsse.pizza.consumer.server.model.order;

import lombok.Data;

@Data
public class PayOrderRequest {
    private String orderId;
    private double totalPrice;
}
