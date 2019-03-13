package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import lombok.Data;

@Data
public class CancelOrderResponse extends Response {
    public CancelOrderResponse() {
    }

    public CancelOrderResponse(ConsumerServerException e) {
        super(e);
    }
}
