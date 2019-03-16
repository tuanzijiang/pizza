package edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order;

import edu.ecnu.scsse.pizza.bussiness.server.exception.BusinessServerException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;

import java.util.List;

public class OrderDetailResponse extends BaseResponse {
    private String orderId;
    private String receiveName;
    private String receivePhone;
    private String receiveAddress;
    private List<Menu> menuList;
    private double totalAmount;
    private String buyPhone;
    private String commitTime;
    private String shopId;
    private String shopName;
    private String driverId;
    private String driverName;
    private String driverPhone;
    private String startDeliverTime;
    private String arriveTime;
    private OrderStatus state;

    public OrderDetailResponse(){

    }

    public OrderDetailResponse(Order order) {
        //CopyUtils.copyProperties(order,this);
        this.orderId = order.getOrderId();
        this.receiveName = order.getReceiveName();
        this.receivePhone = order.getReceivePhone();
        this.receiveAddress = order.getReceiveAddress();
        this.menuList = order.getMenuList();
        this.totalAmount = order.getTotalAmount();
        this.buyPhone = order.getBuyPhone();
        this.commitTime = order.getCommitTime();
        this.shopId = order.getShopId();
        this.shopName = order.getShopName();
        this.driverId = order.getDriverId();
        this.driverName = order.getDriverName();
        this.driverPhone = order.getDriverPhone();
        this.startDeliverTime = order.getStartDeliverTime();
        this.arriveTime= order.getArriveTime();
        this.state = order.getState();
    }

    public OrderDetailResponse(BusinessServerException e) {
        super(e);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBuyPhone() {
        return buyPhone;
    }

    public void setBuyPhone(String buyPhone) {
        this.buyPhone = buyPhone;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
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

    public String getStartDeliverTime() {
        return startDeliverTime;
    }

    public void setStartDeliverTime(String startDeliverTime) {
        this.startDeliverTime = startDeliverTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public OrderStatus getState() {
        return state;
    }

    public void setState(OrderStatus state) {
        this.state = state;
    }
}
