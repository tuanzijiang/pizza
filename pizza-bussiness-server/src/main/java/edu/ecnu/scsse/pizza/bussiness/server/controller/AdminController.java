package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Logger;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.admin.LoginResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.AdminService;

import edu.ecnu.scsse.pizza.bussiness.server.service.OperateLoggerService;
import edu.ecnu.scsse.pizza.data.domain.OperateLoggerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@CrossOrigin
public class AdminController{
    @Autowired
    private AdminService adminService;

    @Autowired
    private OperateLoggerService loggerService;

    /**
    * 管理员登录
    * @param loginRequest
    * @return
    */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse adminLogin(@RequestBody LoginRequest loginRequest){
        return adminService.adminLogin(loginRequest);
    }

    /**
     * 管理员操作日志
     * @param
     * @return loggerList
     */
    @RequestMapping(value = "/getOperateLogger",method = RequestMethod.GET)
    @ResponseBody
    public List<Logger> getOperateLogger(){
        return loggerService.getOperateLogger();
    }
}
