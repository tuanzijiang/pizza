package edu.ecnu.scsse.pizza.consumer.server.controller;

import edu.ecnu.scsse.pizza.consumer.server.model.LoginRequest;
import edu.ecnu.scsse.pizza.consumer.server.model.LoginResponse;
import edu.ecnu.scsse.pizza.consumer.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
