package edu.ecnu.scsse.pizza.bussiness.server.model;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;

public class LoginResponse extends BaseResponse{
    private int adminId;

    public LoginResponse(){}

    public LoginResponse(int adminId) {
        this.adminId = adminId;
    }

    public LoginResponse(BusinessServerException e) {
        super(e);
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
