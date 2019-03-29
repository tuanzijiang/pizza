package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

public class IngredientDetailResponse extends BaseResponse {
    public IngredientDetailResponse() {
    }

    public IngredientDetailResponse(BusinessServerException e) {
        super(e);
    }
}
