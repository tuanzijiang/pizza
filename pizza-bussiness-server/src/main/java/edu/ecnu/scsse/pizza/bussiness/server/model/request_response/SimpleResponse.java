package edu.ecnu.scsse.pizza.bussiness.server.model.request_response;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;

public class SimpleResponse extends BaseResponse {
    public SimpleResponse() {
    }

    public SimpleResponse(BusinessServerException e) {
        super(e);
    }
}
