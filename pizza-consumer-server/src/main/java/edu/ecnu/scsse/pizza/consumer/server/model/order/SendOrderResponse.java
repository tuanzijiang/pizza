package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import lombok.Data;

@Data
public class SendOrderResponse extends Response {
    private Order order;

    public SendOrderResponse() {
    }

    public SendOrderResponse(ConsumerServerException e) {
        super(e);
    }
}
