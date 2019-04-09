package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.PermissionException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/menu")
@CrossOrigin
public class MenuController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    MenuService menuService;

    /**
     * 查看菜单列表
     * @request
     * @return response
     */
    @RequestMapping(value = "/getMenuList",method = RequestMethod.GET)
    @ResponseBody
    public List<MenuDetailResponse> getMenuList(){
        return menuService.getMenuList();
    }

    /**
     * 查看菜品类别列表
     * @request
     * @return response
     */
    @RequestMapping(value = "/getTagList",method = RequestMethod.GET)
    @ResponseBody
    public List<String> getTagList(){
        return menuService.getTagList();
    }

    /**
     * 修改菜单状态
     * @request request
     * @return response
     */
    @RequestMapping(value = "/editMenuStatus",method = RequestMethod.GET)
    @ResponseBody
    public SimpleResponse editMenuStatus(@RequestParam int menuId,@RequestParam int adminId){
        //int adminId = getCurrentAdminId();
        if(adminId!=-1)
            return menuService.editMenuStatus(menuId, adminId);
        else{
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new SimpleResponse(e);
        }
    }

    /**
     * 修改菜单信息
     * @request request
     * @return response
     */
    @RequestMapping(value = "/editMenuDetail",method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse editMenuStatus(@RequestBody MenuDetailRequest menuDetailRequest,@RequestParam int adminId) throws BusinessServerException {
        //int adminId = getCurrentAdminId();
        if (adminId != -1)
            return menuService.editMenuDetail(menuDetailRequest, adminId);
        else {
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new SimpleResponse(e);
        }
    }

    /***
     * 新增披萨
     * @request
     * @return
     */
    @RequestMapping(value = "/addNewMenu",method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse addNewMenu(@RequestBody MenuDetailRequest request,@RequestParam int adminId) throws ParseException,BusinessServerException{
        //int adminId = getCurrentAdminId();
        if(adminId!=-1) {
            return menuService.addNewMenu(request, adminId);
        }
        else{
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new SimpleResponse(e);
        }
    }

    /***
     * 上传披萨图片
     * @request
     * @return
     */
    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam MultipartFile file) throws ParseException,BusinessServerException{
        return menuService.uploadImage(file);
    }


}
