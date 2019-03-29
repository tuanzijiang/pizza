package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.PermissionException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.BatchImportResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient.IngredientManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu.MenuDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<Ingredient> getIngredientList(){
        return ingredientService.getIngredientList();
    }

    /**
     * 批量导入原料（以excel文件形式）
     * @request
     * @return response
     */
    @RequestMapping(value = "/batchImportByExcelFile",method = RequestMethod.POST)
    @ResponseBody
    public BatchImportResponse batchImportByExcelFile(@RequestParam String path){
        return ingredientService.batchImportByExcelFile(path);
    }

    /**
     * 修改原料详情
     * @request
     * @return response
     */
    @RequestMapping(value = "/editIngredientDetail",method = RequestMethod.POST)
    @ResponseBody
    public IngredientDetailResponse editIngredientDetail(@RequestBody IngredientDetailRequest request) throws BusinessServerException{
        int adminId = getCurrentAdminId();
        if (adminId != -1)
            return ingredientService.editIngredientDetail(request);
        else {
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new IngredientDetailResponse(e);
        }
    }

    /**
     * 新增原料
     * @request
     * @return response
     */
    @RequestMapping(value = "/addNewIngredient",method = RequestMethod.POST)
    @ResponseBody
    public IngredientDetailResponse addNewIngredient(@RequestBody IngredientDetailRequest request) throws BusinessServerException{
        int adminId = getCurrentAdminId();
        if (adminId != -1)
            return ingredientService.addNewIngredient(request);
        else {
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new IngredientDetailResponse(e);
        }
    }

    /**
     * 修改原料状态
     * @request
     * @return response
     */
    @RequestMapping(value = "/editIngredientStatus",method = RequestMethod.GET)
    @ResponseBody
    public IngredientDetailResponse editIngredientStatus(@RequestParam int ingredientId) throws BusinessServerException{
        int adminId = getCurrentAdminId();
        if (adminId != -1)
            return ingredientService.editIngredientStatus(ingredientId);
        else {
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new IngredientDetailResponse(e);
        }
    }
}
