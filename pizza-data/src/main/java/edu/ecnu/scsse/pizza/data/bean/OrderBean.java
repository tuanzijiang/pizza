package edu.ecnu.scsse.pizza.data.bean;

import javax.persistence.Column;
import javax.persistence.EntityResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import java.sql.Timestamp;

//@SqlResultSetMapping(name="orderBean",entities=@EntityResult(entityClass=OrderBean.class))
//@Table(name="order_bean")
public class OrderBean {
    private Integer id;
    private Integer addressId;
    private Integer state;
    private Double totalAmount;
    private Integer driverId;
    private Integer shopId;
    private Timestamp commitTime;
    private Timestamp startDeliverTime;
    private Timestamp arriveTime;
    private String orderUuid;

    private String shopName;
    private String buyPhone;

    private String receiveName;
    private String receivePhone;
    private String receiveAddress;

    private String driverName;
    private String driverPhone;

    public OrderBean() {
    }

    public OrderBean(Integer id, Integer addressId, Integer state, Double totalAmount, Integer driverId, Integer shopId, Timestamp commitTime, Timestamp startDeliverTime, Timestamp arriveTime, String orderUuid, String shopName, String buyPhone, String receiveName, String receivePhone, String receiveAddress, String driverName, String driverPhone) {
        this.id = id;
        this.addressId = addressId;
        this.state = state;
        this.totalAmount = totalAmount;
        this.driverId = driverId;
        this.shopId = shopId;
        this.commitTime = commitTime;
        this.startDeliverTime = startDeliverTime;
        this.arriveTime = arriveTime;
        this.orderUuid = orderUuid;
        this.shopName = shopName;
        this.buyPhone = buyPhone;
        this.receiveName = receiveName;
        this.receivePhone = receivePhone;
        this.receiveAddress = receiveAddress;
        this.driverName = driverName;
        this.driverPhone = driverPhone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Timestamp commitTime) {
        this.commitTime = commitTime;
    }

    public Timestamp getStartDeliverTime() {
        return startDeliverTime;
    }

    public void setStartDeliverTime(Timestamp startDeliverTime) {
        this.startDeliverTime = startDeliverTime;
    }

    public Timestamp getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Timestamp arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getBuyPhone() {
        return buyPhone;
    }

    public void setBuyPhone(String buyPhone) {
        this.buyPhone = buyPhone;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }
}
