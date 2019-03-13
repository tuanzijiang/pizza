package edu.ecnu.scsse.pizza.consumer.server.model.user;

import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import lombok.Data;

@Data
public class AddUserAddressResponse extends Response {

    private Address address;

}
