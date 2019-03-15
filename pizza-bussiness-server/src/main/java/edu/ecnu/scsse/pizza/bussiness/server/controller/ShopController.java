package edu.ecnu.scsse.pizza.bussiness.server.controller;

import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.shop.ShopManageResponse;
import edu.ecnu.scsse.pizza.bussiness.server.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shop")
public class ShopController {
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
    public ShopManageResponse getShopList(){
        return shopService.getShopList();
    }

}
