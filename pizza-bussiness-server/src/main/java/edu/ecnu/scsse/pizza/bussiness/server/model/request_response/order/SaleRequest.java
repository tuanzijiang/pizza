package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order;

public class SaleRequest {
    private String startDate;
    private String endDate;

    public SaleRequest() {
    }

    public SaleRequest(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
