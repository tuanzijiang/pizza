package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import lombok.Data;

@Data
public class FetchOrderResponse extends Response {
    private Order order;

    public FetchOrderResponse() {
    }

    public FetchOrderResponse(ConsumerServerException e) {
        super(e);
    }

}
