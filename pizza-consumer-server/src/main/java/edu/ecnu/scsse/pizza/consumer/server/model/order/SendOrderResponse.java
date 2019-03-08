package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import lombok.Data;

@Data
public class SendOrderResponse {
    private Order order;
}
