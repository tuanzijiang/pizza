package edu.ecnu.scsse.pizza.data.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "operate_logger", schema = "pizza_project", catalog = "")
public class OperateLoggerEntity {
    private int id;
    private int adminId;
    private String operateType;
    private String operateDetail;
    private Timestamp operateTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "admin_id")
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Basic
    @Column(name = "operate_type")
    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    @Basic
    @Column(name = "operate_detail")
    public String getOperateDetail() {
        return operateDetail;
    }

    public void setOperateDetail(String operateDetail) {
        this.operateDetail = operateDetail;
    }

    @Basic
    @Column(name = "operate_time")
    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperateLoggerEntity that = (OperateLoggerEntity) o;

        if (id != that.id) return false;
        if (adminId != that.adminId) return false;
        if (operateType != null ? !operateType.equals(that.operateType) : that.operateType != null) return false;
        if (operateDetail != null ? !operateDetail.equals(that.operateDetail) : that.operateDetail != null)
            return false;
        if (operateTime != null ? !operateTime.equals(that.operateTime) : that.operateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + adminId;
        result = 31 * result + (operateType != null ? operateType.hashCode() : 0);
        result = 31 * result + (operateDetail != null ? operateDetail.hashCode() : 0);
        result = 31 * result + (operateTime != null ? operateTime.hashCode() : 0);
        return result;
    }
}
