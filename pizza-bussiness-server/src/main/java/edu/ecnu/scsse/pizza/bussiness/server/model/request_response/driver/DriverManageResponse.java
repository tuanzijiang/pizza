package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Driver;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

import java.util.List;

public class DriverManageResponse extends BaseResponse{
    private List<Driver> driverList;

    public DriverManageResponse() {
    }

    public DriverManageResponse(BusinessServerException e) {
        super(e);
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

}
