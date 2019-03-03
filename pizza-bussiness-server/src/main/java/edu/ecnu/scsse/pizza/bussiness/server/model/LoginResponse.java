package edu.ecnu.scsse.pizza.bussiness.server.model;

public class LoginResponse extends BaseResponse{
    private int adminId;

    public LoginResponse() {
    }

    public LoginResponse(int adminId) {
        this.adminId = adminId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
