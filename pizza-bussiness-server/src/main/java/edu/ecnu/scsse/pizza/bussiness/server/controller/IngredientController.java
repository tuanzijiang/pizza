package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.PermissionException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.ShopIngredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.*;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/ingredient")
@CrossOrigin
public class IngredientController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    IngredientService ingredientService;

    /**
     * 查看原料列表
     * @request
     * @return response
     */
    @RequestMapping(value = "/getIngredientList",method = RequestMethod.GET)
    @ResponseBody
    public List<IngredientDetailResponse> getIngredientList(){
        return ingredientService.getIngredientList();
    }

    /**
     * 批量导入原料（以excel文件形式）
     * @request
     * @return response
     */
    @RequestMapping(value = "/batchImportByExcelFile",method = RequestMethod.POST)
    @ResponseBody
    public BatchImportResponse batchImportByExcelFile(@RequestParam MultipartFile file){
        return ingredientService.batchImportByExcelFile(file);
    }

    /**
     * 修改原料详情
     * @request
     * @return response
     */
    @RequestMapping(value = "/editIngredientDetail",method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse editIngredientDetail(@RequestBody IngredientDetailRequest request,@RequestParam int adminId) throws BusinessServerException{
        //int adminId = getCurrentAdminId();
        if (adminId != -1)
            return ingredientService.editIngredientDetail(request);
        else {
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new SimpleResponse(e);
        }
    }

    /**
     * 新增原料
     * @request
     * @return response
     */
    @RequestMapping(value = "/addNewIngredient",method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse addNewIngredient(@RequestBody IngredientDetailRequest request,@RequestParam int adminId) throws BusinessServerException{
        //int adminId = getCurrentAdminId();
        if (adminId != -1)
            return ingredientService.addNewIngredient(request);
        else {
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new SimpleResponse(e);
        }
    }

    /**
     * 修改原料状态
     * @request
     * @return response
     */
    @RequestMapping(value = "/editIngredientStatus",method = RequestMethod.GET)
    @ResponseBody
    public SimpleResponse editIngredientStatus(@RequestParam int ingredientId,@RequestParam int adminId) throws BusinessServerException{
        //int adminId = getCurrentAdminId();
        if (adminId != -1)
            return ingredientService.editIngredientStatus(ingredientId);
        else {
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new SimpleResponse(e);
        }
    }


    /**
     * 原料预警列表
     * @request
     * @return response
     */
    @RequestMapping(value = "/getAlarmList",method = RequestMethod.GET)
    @ResponseBody
    public List<ShopIngredient> getAlarmList(){
        return ingredientService.getAlarmList();
    }

    /**
     * 确认订购原料
     * @request
     * @return response
     */
    @RequestMapping(value = "/buyIngredient",method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse buyIngredient(@RequestBody BuyIngredientRequest request,@RequestParam int adminId){
        //int adminId = getCurrentAdminId();
        if(adminId!=-1) {
            return ingredientService.buyIngredient(request);
        }
        else{
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new SimpleResponse(e);
        }

    }

    /**
     * 删除原料
     * @request
     * @return response
     */
    @RequestMapping(value = "/deleteIngredient",method = RequestMethod.GET)
    @ResponseBody
    public SimpleResponse deleteIngredient(@RequestParam int id,@RequestParam int adminId){
        //int adminId = getCurrentAdminId();
        if(adminId!=-1) {
            return ingredientService.deleteIngredient(id);
        }
        else{
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new SimpleResponse(e);
        }

    }

}
