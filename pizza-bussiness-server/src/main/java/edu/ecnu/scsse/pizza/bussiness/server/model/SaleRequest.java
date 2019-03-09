package edu.ecnu.scsse.pizza.bussiness.server.model;

public class SaleRequest {
    private String startDate;
    private String endDate;

    public SaleRequest() {
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
