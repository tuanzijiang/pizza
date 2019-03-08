package edu.ecnu.scsse.pizza.consumer.server.model.user;


import lombok.Data;

@Data
public class SignUpRequest {
    private String phone;
    private String password;
    private String email;

}
