package edu.ecnu.scsse.pizza.consumer.server.model.user;

import edu.ecnu.scsse.pizza.data.enums.UpdateUserType;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private int userId;
    private UpdateUserType type;
    private String value;
}
