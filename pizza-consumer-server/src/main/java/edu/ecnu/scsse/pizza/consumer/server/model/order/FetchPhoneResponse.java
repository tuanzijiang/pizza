package edu.ecnu.scsse.pizza.consumer.server.model.order;

import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import lombok.Data;

@Data
public class FetchPhoneResponse extends Response {
    private String deliverymanPhone;
    private String shopPhone;
    private String servicePhone;
}
