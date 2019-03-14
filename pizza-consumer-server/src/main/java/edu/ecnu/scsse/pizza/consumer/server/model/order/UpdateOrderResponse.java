package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import lombok.Data;

@Data
public class UpdateOrderResponse extends Response {

    public UpdateOrderResponse() {
    }

    public UpdateOrderResponse(ConsumerServerException e) {
        super(e);
    }
}
