package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin;

import javax.servlet.http.HttpServletRequest;

public class LoginRequest{
    private String adminName;
    private String password;

    public LoginRequest(){}

    public LoginRequest(String adminName, String password) {
        this.adminName = adminName;
        this.password = password;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
