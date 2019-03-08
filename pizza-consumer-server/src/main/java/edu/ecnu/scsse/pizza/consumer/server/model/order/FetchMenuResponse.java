package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Pizza;
import lombok.Data;

import java.util.List;

@Data
public class FetchMenuResponse extends Response {

    private List<Pizza> pizzas;

    private Order cart;
}
