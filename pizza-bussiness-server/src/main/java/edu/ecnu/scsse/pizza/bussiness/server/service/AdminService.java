package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.model.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.repository.*;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminJpaRepository adminJpaRepository;

    public LoginResponse adminLogin(LoginRequest loginRequest){
        Optional<AdminEntity> admin = adminJpaRepository.findByUsername(loginRequest.getAdminName());
        LoginResponse loginResponse = new LoginResponse(1);
        loginResponse.setReturnCode("200");
        loginResponse.setErrorMessage("登录成功");
        return loginResponse;
    }
}
