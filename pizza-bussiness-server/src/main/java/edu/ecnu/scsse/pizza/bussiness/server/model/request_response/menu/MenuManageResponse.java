package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

import java.util.List;

public class MenuManageResponse extends BaseResponse {
    private List<Menu> menuList;

    public MenuManageResponse(){

    }

    public MenuManageResponse(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public MenuManageResponse(BusinessServerException e) {
        super(e);
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
