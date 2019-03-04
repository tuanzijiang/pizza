package edu.ecnu.scsse.pizza.data.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order", schema = "pizza_project", catalog = "")
public class OrderEntity {
    private int id;
    private String orderId;
    private int userId;
    private int addressId;
    private int state;
    private Double totalPrice;
    private Integer driverId;
    private Integer shopId;
    private Timestamp commitTime;
    private Timestamp deliverStartTime;
    private Timestamp deliverEndTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "orderId")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "address_id")
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Basic
    @Column(name = "state")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Basic
    @Column(name = "total_price")
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Basic
    @Column(name = "driver_id")
    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    @Basic
    @Column(name = "shop_id")
    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "commit_time")
    public Timestamp getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Timestamp commitTime) {
        this.commitTime = commitTime;
    }

    @Basic
    @Column(name = "deliver_start_time")
    public Timestamp getDeliverStartTime() {
        return deliverStartTime;
    }

    public void setDeliverStartTime(Timestamp deliverStartTime) {
        this.deliverStartTime = deliverStartTime;
    }

    @Basic
    @Column(name = "deliver_end_time")
    public Timestamp getDeliverEndTime() {
        return deliverEndTime;
    }

    public void setDeliverEndTime(Timestamp deliverEndTime) {
        this.deliverEndTime = deliverEndTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (userId != that.userId) return false;
        if (addressId != that.addressId) return false;
        if (state != that.state) return false;
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;
        if (driverId != null ? !driverId.equals(that.driverId) : that.driverId != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (commitTime != null ? !commitTime.equals(that.commitTime) : that.commitTime != null) return false;
        if (deliverStartTime != null ? !deliverStartTime.equals(that.deliverStartTime) : that.deliverStartTime != null)
            return false;
        if (deliverEndTime != null ? !deliverEndTime.equals(that.deliverEndTime) : that.deliverEndTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + addressId;
        result = 31 * result + state;
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (driverId != null ? driverId.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (commitTime != null ? commitTime.hashCode() : 0);
        result = 31 * result + (deliverStartTime != null ? deliverStartTime.hashCode() : 0);
        result = 31 * result + (deliverEndTime != null ? deliverEndTime.hashCode() : 0);
        return result;
    }
}
