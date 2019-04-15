package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.exception.PermissionException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Ingredient;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Shop;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopDetailResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopIngredientResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.ShopService;
import edu.ecnu.scsse.pizza.data.bean.ShopIngredientBean;
import edu.ecnu.scsse.pizza.data.domain.IngredientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/shop")
@CrossOrigin
public class ShopController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    ShopService shopService;

    /**
     * 查看工厂列表
     * @request
     * @return response
     */
    @RequestMapping(value = "/getShopList",method = RequestMethod.GET)
    @ResponseBody
    public List<ShopManageResponse> getShopList() throws Exception{
        return shopService.getShopList();
    }

    /***
     * 查看工厂原料库
     * @request
     * @return
     */
    @RequestMapping(value = "/getIngredientListByShopId",method = RequestMethod.GET)
    @ResponseBody
    public List<ShopIngredientBean> getIngredientListByShopId(@RequestParam int shopId) throws Exception{
        return shopService.getIngredientListByShopId(shopId);
    }

    /***
     * 修改工厂信息
     * @request
     * @return
     */
    @RequestMapping(value = "/editShopDetail",method = RequestMethod.POST)
    @ResponseBody
    public ShopDetailResponse editShopDetail(@RequestBody ShopDetailRequest request,@RequestParam int adminId) throws ParseException,BusinessServerException{
        //int adminId = getCurrentAdminId();
        if(adminId!=-1) {
            return shopService.editShopDetail(request, adminId);
        }
        else{
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new ShopDetailResponse(e);
        }
    }

    /***
     * 新增工厂
     * @request
     * @return
     */
    @RequestMapping(value = "/addNewShop",method = RequestMethod.POST)
    @ResponseBody
    public ShopDetailResponse addNewShop(@RequestBody ShopDetailRequest request,@RequestParam int adminId) throws ParseException,BusinessServerException{
        //int adminId = getCurrentAdminId();
        if(adminId!=-1) {
            return shopService.addNewShop(request, adminId);
        }
        else{
            PermissionException e = new PermissionException("Admin is logout.");
            log.warn("Admin is logout.", e);
            return new ShopDetailResponse(e);
        }
    }

    /***
     * 上传工厂图片
     * @request
     * @return
     */
    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
    @ResponseBody
    public SimpleResponse uploadImage(@RequestParam MultipartFile file, @RequestParam int shopId) throws ParseException,BusinessServerException{
        return shopService.uploadShopImageFile(file,shopId);
    }

}
