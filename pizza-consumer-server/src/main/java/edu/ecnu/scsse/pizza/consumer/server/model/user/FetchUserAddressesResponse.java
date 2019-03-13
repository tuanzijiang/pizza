package edu.ecnu.scsse.pizza.consumer.server.model.user;

import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Address;
import lombok.Data;

import java.util.List;

@Data
public class FetchUserAddressesResponse extends Response {
    private List<Address> addresses;
}
