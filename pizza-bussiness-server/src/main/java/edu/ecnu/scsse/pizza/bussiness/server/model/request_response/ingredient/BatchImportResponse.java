package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ingredient;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

public class BatchImportResponse extends BaseResponse {
//    private int num;

    public BatchImportResponse() {
    }

    public BatchImportResponse(BusinessServerException e) {
        super(e);
    }
}
