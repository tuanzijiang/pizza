package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.SaleStatus;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

import java.util.List;

public class SaleResponse extends BaseResponse {
    private List<SaleStatus> saleStatusList;

    public SaleResponse() {
    }

    public SaleResponse(List<SaleStatus> saleStatusList) {
        this.saleStatusList = saleStatusList;
    }

    public SaleResponse(BusinessServerException e) {
        super(e);
    }

    public List<SaleStatus> getSaleStatusList() {
        return saleStatusList;
    }

    public void setSaleStatusList(List<SaleStatus> saleStatusList) {
        this.saleStatusList = saleStatusList;
    }
}
