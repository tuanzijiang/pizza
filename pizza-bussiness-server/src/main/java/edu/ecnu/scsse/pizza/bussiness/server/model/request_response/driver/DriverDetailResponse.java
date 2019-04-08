package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;

public class DriverDetailResponse extends BaseResponse{
    private int driverId;

    public DriverDetailResponse(int id) {
        this.driverId = id;
    }

    public DriverDetailResponse(BusinessServerException e) {
        super(e);
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public DriverDetailResponse() { }

}
