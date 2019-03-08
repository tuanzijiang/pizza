package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class FetchOrdersResponse extends Response {

    private List<Order> orders;

    public FetchOrdersResponse() {
    }

    public FetchOrdersResponse(ConsumerServerException e) {
        super(e);
    }
}
