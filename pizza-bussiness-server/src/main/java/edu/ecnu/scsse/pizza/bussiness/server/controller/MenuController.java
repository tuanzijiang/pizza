package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    /**
     * 查看菜单列表
     * @request
     * @return response
     */
    @RequestMapping(value = "/getMenuList",method = RequestMethod.GET)
    @ResponseBody
    public MenuManageResponse getMenuList(){
        return menuService.getMenuList();
    }

    /**
     * 修改菜单状态
     * @request request
     * @return response
     */
    @RequestMapping(value = "/editMenuStatus/{menuId}",method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse editMenuStatus(@PathVariable int menuId){
        return menuService.editMenuStatus(menuId);
    }

    /**
     * 修改菜单信息
     * @request request
     * @return response
     */
    @RequestMapping(value = "/editMenuDetail",method = RequestMethod.GET)
    @ResponseBody
    public MenuDetailResponse editMenuStatus(@RequestBody MenuDetailRequest request) throws BusinessServerException {
        return menuService.editMenuDetail(request);
    }


}
