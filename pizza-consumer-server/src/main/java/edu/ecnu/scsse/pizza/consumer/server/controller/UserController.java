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
     *
     * @param fetchUserRequest
     * @return
     */
    @RequestMapping(value = "/fetchUser", method = RequestMethod.POST)
    @ResponseBody
    FetchUserResponse fetchUser(@RequestBody FetchUserRequest fetchUserRequest) {
        return userService.fetchUser(fetchUserRequest.getUserId());
    }

    /**
     * 更新当前用户信息
     *
     * @param updateUserRequest
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    UpdateUserResponse updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    @RequestMapping(value = "/fetchLoginStatus", method = RequestMethod.POST)
    @ResponseBody
    LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }


    /**
     * 发送验证码
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendVerificationCode", method = RequestMethod.POST)
    @ResponseBody
    SendVerificationCodeResponse sendVerificationCode(@RequestBody SendVerificationCodeRequest request) {
        return userService.sendVerificationCode(request);
    }

    /**
     * 退出
     *
     * @param logoutRequest
     * @return
     */
    @RequestMapping(value = "/fetchLogoutStatus", method = RequestMethod.POST)
    @ResponseBody
    LogoutResponse logout(@RequestBody LogoutRequest logoutRequest) {
        return userService.logout(logoutRequest);
    }


    /**
     * 注册
     *
     * @param signUpRequest
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return userService.signUp(signUpRequest);
    }



    /**
     * 获取当前用户的地址信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/fetchUserAddresses")
    @ResponseBody
    public FetchUserAddressesResponse fetchUserAddresses(@RequestBody FetchUserAddressesRequest request) {
        return userService.fetchUserAddresses(request);

    }

    /**
     * 更新当前用户的地址信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateUserAddress")
    @ResponseBody
    public AddUserAddressResponse updateUserAddress(@RequestBody AddUserAddressRequest request) {
        return userService.updateUserAddress(request);

    }


    /**
     * 添加地址信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/addUserAddress")
    @ResponseBody
    public AddUserAddressResponse addUserAddress(@RequestBody AddUserAddressRequest request) {
        return userService.addUserAddress(request);

    }


}
