package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.model.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    public LoginResponse adminLogin(LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse(1);
        loginResponse.setReturnCode("200");
        loginResponse.setErrorMessage("登录成功");
        return loginResponse;
    }
}
