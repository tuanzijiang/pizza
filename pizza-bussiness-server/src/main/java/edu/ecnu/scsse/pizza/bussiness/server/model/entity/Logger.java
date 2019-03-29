package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

public class Logger {
    private int id;
    private int adminId;
    private String operateType;
    private String operateDetail;
    private String operateTime;

    public Logger() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateDetail() {
        return operateDetail;
    }

    public void setOperateDetail(String operateDetail) {
        this.operateDetail = operateDetail;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
}
