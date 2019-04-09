package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.PermissionException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Driver;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/driver")
@CrossOrigin
public class DriverController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    DriverService driverService;

    /**
     * 查看配送员列表
     * @request
     * @return response
     */
    @RequestMapping(value = "/getDriverList",method = RequestMethod.GET)
    @ResponseBody
    public List<DriverManageResponse> getDriverList() throws Exception{
        return driverService.getDriverList();
    }

    /***
     * 修改配送员信息
     * @request
     * @return
     */
    @RequestMapping(value = "/editDriverDetail",method = RequestMethod.POST)
    @ResponseBody
    public DriverDetailResponse editShopDetail(@RequestBody DriverDetailRequest request,@RequestParam int adminId) throws ParseException,BusinessServerException {
        //int adminId = getCurrentAdminId();
        if (adminId != -1) {
            return driverService.editDriverDetail(request, adminId);
        } else {
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new DriverDetailResponse(e);
        }
    }

    /***
     * 新增配送员
     * @request
     * @return
     */
    @RequestMapping(value = "/addNewDriver",method = RequestMethod.POST)
    @ResponseBody
    public DriverDetailResponse addNewDriver(@RequestBody DriverDetailRequest request,@RequestParam int adminId) throws ParseException,BusinessServerException{
        //int adminId = getCurrentAdminId();
        if(adminId!=-1) {
            return driverService.addNewDriver(request, adminId);
        }
        else{
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new DriverDetailResponse(e);
        }
    }

    /**
     * 删除配送员
     * @request
     * @return response
     */
    @RequestMapping(value = "/removeDriver",method = RequestMethod.GET)
    @ResponseBody
    public SimpleResponse deleteDriver(@RequestParam int id, @RequestParam int adminId){
        //int adminId = getCurrentAdminId();
        if(adminId!=-1) {
            return driverService.deleteDriver(id, adminId);
        }
        else{
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new SimpleResponse(e);
        }

    }

}
