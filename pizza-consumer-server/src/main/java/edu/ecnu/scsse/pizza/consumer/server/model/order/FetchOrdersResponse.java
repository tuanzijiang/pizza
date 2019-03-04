package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;

import java.util.List;

public class FetchOrdersResponse extends Response {

    private List<Order> orders;

    public FetchOrdersResponse() {
    }

    public FetchOrdersResponse(ConsumerServerException e) {
        super(e);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
