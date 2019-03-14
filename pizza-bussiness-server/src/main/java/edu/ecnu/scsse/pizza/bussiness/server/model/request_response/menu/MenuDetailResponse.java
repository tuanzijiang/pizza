package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.menu;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

public class MenuDetailResponse extends BaseResponse{
    public MenuDetailResponse() {
    }

    public MenuDetailResponse(BusinessServerException e) {
        super(e);
    }
}
