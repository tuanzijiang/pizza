package edu.ecnu.scsse.pizza.consumer.server.model.user;

import edu.ecnu.scsse.pizza.consumer.server.model.Response;
import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.User;
import lombok.Data;
import org.omg.PortableInterceptor.USER_EXCEPTION;

@Data
public class SignUpResponse extends Response {

    private User user;

}
