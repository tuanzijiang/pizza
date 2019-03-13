package edu.ecnu.scsse.pizza.consumer.server.model.user;

import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import lombok.Data;

@Data
public class AddUserAddressRequest {

    private Integer userId;

    private Address address;

}
