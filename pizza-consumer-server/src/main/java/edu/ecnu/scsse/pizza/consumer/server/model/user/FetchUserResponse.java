package edu.ecnu.scsse.pizza.consumer.server.model.user;

import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.User;
import lombok.Data;

@Data
public class FetchUserResponse extends Response {

    User user;

}
