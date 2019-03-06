package edu.ecnu.scsse.pizza.bussiness.server.controller;


import edu.ecnu.scsse.pizza.bussiness.server.model.UserManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserManageController {
    @Autowired
    private UserManageService userManageService;

    @RequestMapping(value = "/getUserList",method = RequestMethod.GET)
    @ResponseBody
    public UserManageResponse adminLogin(){
        return userManageService.getUserList();
    }
}
