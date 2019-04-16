package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OrderState;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;

import java.sql.Timestamp;
import java.util.*;

public class Order implements Comparable<Order>{
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
    private String orderUuid;

    private Point mapPoint;
    private int deliveryPriority;
    private long latestReceiveTime;
    private long deliveryDuration;

    @Override
    public int compareTo(Order o) {
        return Timestamp.valueOf(this.commitTime).before(Timestamp.valueOf(o.getCommitTime()))? -1 : 1;
    }

    public Order() {
        this.orderId = "";
        this.receiveName = "";
        this.receivePhone = "";
        this.receiveAddress = "";
        this.menuList = new ArrayList<>();
        this.totalAmount = 0.0;
        this.buyPhone = "";
        this.commitTime = "";
        this.shopId = "";
        this.shopName = "";
        this.driverId = "";
        this.driverName = "";
        this.driverPhone = "";
        this.startDeliverTime = "";
        this.arriveTime = "";
        this.state = OrderStatus.UNKNOWN;
    }


    public Map<Integer,Integer> getOrderIngredientMap(){
        Map<Integer,Integer> ingredientNumMap=new HashMap<>();
        for(Menu menu:menuList){
            List<Ingredient> ingredientList=menu.getIngredients();
            for(Ingredient ingredient:ingredientList){
                if(ingredientNumMap.containsKey(ingredient.getId())){
                    ingredientNumMap.put(ingredient.getId(),ingredientNumMap.get(ingredient.getId())+ingredient.getCount()*menu.getCount());
                }else{
                    ingredientNumMap.put(ingredient.getId(),ingredient.getCount()*menu.getCount());
                }
            }
        }
        return ingredientNumMap;
    }

    public int getOrderMenuNum(){
        int cnt=0;
        for(Menu menu:menuList){
            cnt+=menu.getCount();
        }
        return cnt;
    }

    public String getOrderListInfo(List<Order> orderList){
        String rst="";
        for(Order order :orderList){
            rst+=("orderId:"+order.getOrderId()+"orderMenuNum:"+order.getOrderMenuNum()+" ");
        }
        return rst;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
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

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Point getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(Point mapPoint) {
        this.mapPoint = mapPoint;
    }


    public int getDeliveryPriority() {
        return deliveryPriority;
    }

    public void setDeliveryPriority(int deliveryPriority) {
        this.deliveryPriority = deliveryPriority;
    }



    public long getLatestReceiveTime() {
        return latestReceiveTime;
    }

    public void setLatestReceiveTime(long latestReceiveTime) {
        this.latestReceiveTime = latestReceiveTime;
    }

    public long getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(long deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", receiveName='" + receiveName + '\'' +
                ", receivePhone='" + receivePhone + '\'' +
                ", receiveAddress='" + receiveAddress + '\'' +
                ", menuList=" + menuList +
                ", totalAmount=" + totalAmount +
                ", buyPhone='" + buyPhone + '\'' +
                ", commitTime='" + commitTime + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", driverId='" + driverId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", startDeliverTime='" + startDeliverTime + '\'' +
                ", arriveTime='" + arriveTime + '\'' +
                ", state=" + state +
                ", orderUuid='" + orderUuid + '\'' +
                ", mapPoint=" + mapPoint +
                ", deliveryPriority=" + deliveryPriority +
                ", latestReceiveTime=" + latestReceiveTime +
                ", deliveryDuration=" + deliveryDuration +
                '}';
    }
}
