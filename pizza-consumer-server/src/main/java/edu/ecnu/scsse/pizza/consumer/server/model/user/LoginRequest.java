package edu.ecnu.scsse.pizza.consumer.server.model.user;

import lombok.Data;

import javax.lang.model.element.TypeElement;

@Data
public class LoginRequest {
    public enum Type{
        PASSWORD(0),
        VERIFICATION(1);

        int value;
        Type(int value){
            this.value=value;
        }

    }

    private Type type;
    private String account; // email
    private String password;

}
