package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.model.PayType;
import lombok.Data;

@Data
public class PayOrderRequest {
    private String orderId;
    private double totalPrice;
    private PayType type;
}
