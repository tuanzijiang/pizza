package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

public class NewMenuResponse extends BaseResponse {
    private int menuId;

    public NewMenuResponse() {
    }

    public NewMenuResponse(BusinessServerException e) {
        super(e);
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
