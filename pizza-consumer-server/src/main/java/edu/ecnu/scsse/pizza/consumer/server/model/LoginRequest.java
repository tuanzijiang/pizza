package edu.ecnu.scsse.pizza.consumer.server.model;

public class LoginRequest {

    private LoginAccountType loginAccountType;
    private String account;
    private String password;

    public LoginAccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(LoginAccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
