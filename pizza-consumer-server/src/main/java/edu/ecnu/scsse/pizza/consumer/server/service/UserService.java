package edu.ecnu.scsse.pizza.consumer.server.service;

import edu.ecnu.scsse.pizza.consumer.server.model.LoginRequest;
import edu.ecnu.scsse.pizza.consumer.server.model.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public LoginResponse login(LoginRequest request) {
        log.info(String.format("User Login: account[%s]{type=%s}, password[%s]",
                request.getAccount(), request.getLoginAccountType().name(), request.getPassword()));
        LoginResponse response = new LoginResponse();
        response.setSuccess(true);
        response.setUserName(request.getAccount());
        return response;
    }
}
