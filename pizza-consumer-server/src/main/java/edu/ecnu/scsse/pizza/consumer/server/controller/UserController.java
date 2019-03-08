package edu.ecnu.scsse.pizza.consumer.server.controller;

import edu.ecnu.scsse.pizza.consumer.server.model.user.*;
import edu.ecnu.scsse.pizza.consumer.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    // TODO: 修改参数接收的格式

    @Autowired
    private UserService userService;

    /**
     * 获取当前用户信息
     * @param fetchUserRequest
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    FetchUserResponse getUserInfo(@RequestBody FetchUserRequest fetchUserRequest){
        return userService.getUserInfo(fetchUserRequest.getUserId());
    }

    /**
     * 更新当前用户信息
     * @param updateUserRequest
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    UpdateUserResponse updateUserInfo(@RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUserInfo(updateUserRequest);
    }

    /**
     * 登录
     * @param loginRequest
     * @return
     */
    @RequestMapping(value = "/loginStatus",method = RequestMethod.POST)
    @ResponseBody
    LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    /**
     * 退出
     * @param logoutRequest
     * @return
     */
    @RequestMapping(value = "/logoutStatus",method = RequestMethod.POST)
    @ResponseBody
    LogoutResponse logout(@RequestBody LogoutRequest logoutRequest) {
        return userService.logout(logoutRequest);
    }


    /**
     * 注册
     * @param signUpRequest
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    @ResponseBody
    SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return userService.signUp(signUpRequest);
    }

}
