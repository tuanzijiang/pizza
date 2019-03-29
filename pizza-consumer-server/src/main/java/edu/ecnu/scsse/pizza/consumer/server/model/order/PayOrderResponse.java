package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import lombok.Data;

@Data
public class PayOrderResponse extends Response {
    private String form;

    public PayOrderResponse() {
    }

    public PayOrderResponse(ConsumerServerException e) {
        super(e);
    }
}
