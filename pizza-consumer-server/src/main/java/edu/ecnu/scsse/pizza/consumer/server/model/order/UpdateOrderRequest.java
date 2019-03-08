package edu.ecnu.scsse.pizza.consumer.server.model.order;

import lombok.Data;

@Data
public class UpdateOrderRequest {

    private String orderId;
    private Integer pizzaId;
    private Integer count;
}
