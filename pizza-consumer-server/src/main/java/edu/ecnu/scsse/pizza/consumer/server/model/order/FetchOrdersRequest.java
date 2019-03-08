package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class FetchOrdersRequest {

    private Integer userId;

    private String lastOrderId;

    private Integer num;

    private List<OrderStatus> status;

}
