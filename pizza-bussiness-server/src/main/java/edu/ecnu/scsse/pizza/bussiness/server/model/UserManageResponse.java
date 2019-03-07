package edu.ecnu.scsse.pizza.bussiness.server.model;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.User;

import java.util.List;

public class UserManageResponse extends BaseResponse{
    private List<User> userList;

    public UserManageResponse(){}

    public UserManageResponse(List<User> userList) {
        this.userList = userList;
    }

    public UserManageResponse(BusinessServerException e) {
        super(e);
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
