package edu.ecnu.scsse.pizza.consumer.server.model;

public class LoginResponse extends Response {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
