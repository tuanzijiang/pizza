package edu.ecnu.scsse.pizza.bussiness.server.model.entity;


import edu.ecnu.scsse.pizza.bussiness.server.model.gaode.BicyclingData;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.driver.DriverDetailRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.gaode.GaoDeMapUtil;
import edu.ecnu.scsse.pizza.bussiness.server.service.OrderReceiveService;
import edu.ecnu.scsse.pizza.data.enums.DriverStatus;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.repository.DriverJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.OrderJpaRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.*;


public class Driver {
    private int maxDeliveryNumEachDriver = 8;
    private int id;
    private int shopId;
    private String shopName;
    private String name;
    private String phone;
    private DriverStatus state;
    private List<Order> orderList;
    private Timestamp latestLeaveTime;
    private long backDuration;
    private long sumDeliveryDuration;

    private static long millisecondNumToOneSecond=1000;



    //将order加入骑手队列的得分，判读每个骑手是否接单的凭证
    public DeliverySchedule getDelieveScoreByAddNewOrder(Order waitToDeliveryOrder,Point shopMapPoint){
        DeliverySchedule deliverySchedule = new DeliverySchedule();
        deliverySchedule.setDriverId(id);
        int score=0;
        boolean ableToDelivery=true;
        int sumDeliveryNumEachDriver=0;
        for(Order order:orderList){
            sumDeliveryNumEachDriver+=order.getOrderMenuNum();
        }
        if(maxDeliveryNumEachDriver-sumDeliveryNumEachDriver<waitToDeliveryOrder.getOrderMenuNum()){
            ableToDelivery=false;
        }else {
            if(state == DriverStatus.LEISURE){
                //店家 已接受订单 待接收订单的地图点，两点之间的最短配送时间的二维数组
                List<Point> pointList=new ArrayList<>();
                pointList.add(shopMapPoint);
                for(Order order:orderList){
                    pointList.add(order.getMapPoint());
                }
                List<Order> orders = new ArrayList<>();
                orders.addAll(orderList);
                orders.add(waitToDeliveryOrder);
                deliverySchedule.setOrderList(orders);

                pointList.add(waitToDeliveryOrder.getMapPoint());
                List<List<Double>> pointToPointTime=getPointToPointTime(pointList);
                //n=5 得到 全排列 12345，12354等
                List<List<Integer>> orderPermuList= getPermutaion(orders.size());

                //总配送的最短时间
                double minDeliveryTime=-1;
                //骑手的回程耗时
                double backTime=0;
                //每个订单的配送需要的时间，逐渐累加起来的
                Map<String,Long> minDeliveryTimeEachOrderId = new HashMap<>();
                //遍历全部的订单组合情况
                for(List<Integer> orderRoute:orderPermuList){
                    //计算每个回合的订单的配送需要的时间，逐渐累加起来的
                    Map<String,Long> deliveryTimeEachOrderId = new HashMap<>();
                    //初始化是从店铺到第一个订单的配送时间
                    long deliveryTime = pointToPointTime.get(0).get(orderRoute.get(0)).longValue()*millisecondNumToOneSecond;
                    //依次是从第i个订单到第i+1个定案的配送时间累加,就是到第i+1个订单的配送时间
                    for(int i=0;i<orderRoute.size()-1;i++){
                        deliveryTimeEachOrderId.put(orders.get(orderRoute.get(i)-1).getOrderId(),deliveryTime);
                        deliveryTime += pointToPointTime.get(orderRoute.get(i)).get(orderRoute.get(i+1)).longValue()*millisecondNumToOneSecond;
                    }
                    //无论是orderList没有订单还是有很多和订单最终都会执行
                    deliveryTimeEachOrderId.put(orders.get(orderRoute.get(orderRoute.size()-1)-1).getOrderId(),deliveryTime);
                    //第一次赋值 或者 上面算的总的配送时间小于最短的配送时间,依次赋值给最短配送时间、回店耗时、最短的每个orderId的配送时间
                    if(minDeliveryTime<0 || deliveryTime<minDeliveryTime){
                        minDeliveryTime=deliveryTime;
                        backTime=pointToPointTime.get(orderRoute.get(orderRoute.size()-1)).get(0)*millisecondNumToOneSecond;
                        minDeliveryTimeEachOrderId=deliveryTimeEachOrderId;
                    }
                }

                //当前系统时间
                Timestamp d = new Timestamp(System.currentTimeMillis());
                //初次赋值 最前面最早应该配送的时间戳 为待送订单的最晚配送的时间戳 详细计算为 最晚送达时间-耗时（恒正，但可能超过当前的时间戳，不可直接赋值）
                Timestamp minLatestToDelivery=new Timestamp(waitToDeliveryOrder.getLatestReceiveTime()-minDeliveryTimeEachOrderId.get(waitToDeliveryOrder.getOrderId()));
                //对全部订单计算一遍
                for(Order order:orderList){
                    //leftTime是这个订单还剩余多少时间 详细计算为 订单的最晚送达时间  -当前时间 - 配送时间。为正就是可以有富余，为负就是会超时。
                    long leftTime=order.getLatestReceiveTime()-d.getTime()-minDeliveryTimeEachOrderId.get(order.getOrderId());
                    //计算该订单的最早应该配送的时间戳
                    Timestamp orderMinLatestToDelivery = new Timestamp(order.getLatestReceiveTime()-minDeliveryTimeEachOrderId.get(order.getOrderId()));
                    //更新最前面的最早应该配送的时间戳
                    if(minLatestToDelivery.after(orderMinLatestToDelivery)){
                        minLatestToDelivery=orderMinLatestToDelivery;
                    }
                    //分数机制设为累加
                    score += (leftTime/millisecondNumToOneSecond/60);
                }
                //最后还有待配送订单的还剩余多少时间 没有更新到score去
                score += (waitToDeliveryOrder.getLatestReceiveTime()-d.getTime()-minDeliveryTimeEachOrderId.get(waitToDeliveryOrder.getOrderId())/millisecondNumToOneSecond/60);
                deliverySchedule.setScore(score);
                deliverySchedule.setDeliveryTimeEachOrderId(minDeliveryTimeEachOrderId);
                deliverySchedule.setSumDeliveryDuration(new Double(minDeliveryTime).longValue());
                deliverySchedule.setMinLatestLeaveTime(minLatestToDelivery);
                deliverySchedule.setBackDuration(new Double(backTime).longValue());
            }else {
                ableToDelivery=false;
            }
        }
        deliverySchedule.setAbleToDelivery(ableToDelivery);
        return deliverySchedule;
    }

    //计算给定路线的订单

    //店家 已接受订单 待接收订单的地图点，两点之间的最短配送时间的二维数组
    private List<List<Double>> getPointToPointTime(List<Point> pointList){
        List<List<Double>> pointToPointTime= new ArrayList<>();

        for(int i=0;i<pointList.size();i++){
            List<Double> pointITimeList=new ArrayList<>();
            for(int j=0;j<pointList.size();j++){
                if(i==j){
                    pointITimeList.add(0.0);
                }else {
                    BicyclingData bicyclingData=new GaoDeMapUtil().driveRoutePlan(pointList.get(i),pointList.get(j));
                    if(bicyclingData.getErrcode()==0){
                        pointITimeList.add(bicyclingData.total_duation());
                    }
                }
            }
            pointToPointTime.add(pointITimeList);
        }
        return pointToPointTime;
    }

    private  List<List<Integer>> getPermutaion(int n){
        List<Integer> tmpList=new ArrayList<>();
        List<List<Integer>> resultList= new ArrayList<>();
        for(int i=0;i<n;i++){
            tmpList.add(i+1);
        }
        resultList=permutation(0,tmpList.size()-1,tmpList,resultList);
        return resultList;
    }

    private  List<List<Integer>> permutation(int m,int n,List<Integer> tmpResult,List<List<Integer>> result){
        if(m==n){
            List<Integer> tmp=new ArrayList<>();
            for(Integer i:tmpResult){
                tmp.add(i);
            }
            result.add(tmp);
            return result;
        }else{
            for(int j=m;j<=n;j++){
                tmpResult=swap(tmpResult,m,j);
                result=permutation(m+1,n,tmpResult,result);
                tmpResult=swap(tmpResult,m,j);
            }
            return result;
        }
    }

    //初始化发生异常而奔溃的订单
    public void initDriverOrderList(Point shopMapPoint){
        List<Point> pointList = new ArrayList<>();
        pointList.add(shopMapPoint);
        for(Order order:orderList){
            pointList.add(order.getMapPoint());
        }
        List<List<Double>> pointToPointTime=getPointToPointTime(pointList);
        List<List<Integer>> orderPermuList= getPermutaion(orderList.size());

        //总配送的最短时间
        double minDeliveryTime=-1;
        //骑手的回程耗时
        double backTime=0;
        //每个订单的配送需要的时间，逐渐累加起来的
        Map<String,Long> minDeliveryTimeEachOrderId = new HashMap<>();
        //遍历全部的订单组合情况
        for(List<Integer> orderRoute:orderPermuList){
            //计算每个回合的订单的配送需要的时间，逐渐累加起来的
            Map<String,Long> deliveryTimeEachOrderId = new HashMap<>();
            //初始化是从店铺到第一个订单的配送时间
            long deliveryTime = pointToPointTime.get(0).get(orderRoute.get(0)).longValue()*millisecondNumToOneSecond;
            //依次是从第i个订单到第i+1个定案的配送时间累加,就是到第i+1个订单的配送时间
            for(int i=0;i<orderRoute.size()-1;i++){
                deliveryTimeEachOrderId.put(orderList.get(i).getOrderId(),deliveryTime);
                deliveryTime += pointToPointTime.get(orderRoute.get(i)).get(orderRoute.get(i+1)).longValue()*millisecondNumToOneSecond;
            }
            //第一次赋值 或者 上面算的总的配送时间小于最短的配送时间,依次赋值给最短配送时间、回店耗时、最短的每个orderId的配送时间
            if(minDeliveryTime<0 || deliveryTime<minDeliveryTime){
                minDeliveryTime=deliveryTime;
                backTime=pointToPointTime.get(orderRoute.get(orderRoute.size()-1)).get(0)*millisecondNumToOneSecond;
                minDeliveryTimeEachOrderId=deliveryTimeEachOrderId;
            }
        }

        //当前系统时间
        Timestamp d = new Timestamp(System.currentTimeMillis());
        //初次赋值  最前面最早应该配送的时间戳 第一个订单的最晚配送的时间戳 详细计算为 最晚送达时间-耗时（恒正，但可能超过当前的时间戳，不可直接赋值）
        Timestamp minLatestToDelivery=new Timestamp(orderList.get(0).getLatestReceiveTime()-minDeliveryTimeEachOrderId.get(orderList.get(0).getOrderId()));
        //对全部订单计算一遍
        for(Order order:orderList){
            //leftTime是这个订单还剩余多少时间 详细计算为 订单的最晚送达时间 - 订单提交时间 -当前时间 - 配送时间。为正就是可以有富余，为负就是会超时。
            long leftTime=order.getLatestReceiveTime()-Timestamp.valueOf(order.getCommitTime()).getTime()-d.getTime()-minDeliveryTimeEachOrderId.get(order.getOrderId());
            //计算该订单的最早应该配送的时间戳
            Timestamp orderMinLatestToDelivery = new Timestamp(order.getLatestReceiveTime()-minDeliveryTimeEachOrderId.get(order.getOrderId()));
            //更新最前面的最早应该配送的时间戳
            if(minLatestToDelivery.after(orderMinLatestToDelivery)){
                minLatestToDelivery=orderMinLatestToDelivery;
            }
        }
        backDuration=new Double(backTime).longValue()*millisecondNumToOneSecond;
        latestLeaveTime=minLatestToDelivery;
        sumDeliveryDuration=new Double(minDeliveryTime).longValue()*millisecondNumToOneSecond;
        for(Order order:orderList){
            order.setDeliveryDuration(minDeliveryTimeEachOrderId.get(order.getOrderId()));
        }
    }

    private  List<Integer> swap(List<Integer> tmpResult,int a,int b){
        int tmp;
        tmp = tmpResult.get(a);
        tmpResult.set(a,tmpResult.get(b));
        tmpResult.set(b,tmp);
        return tmpResult;
    }

    public Driver() {
    }

    public Driver(int maxDeliveryNumEachDriver, int id, int shopId, String name, String phone, DriverStatus state, List<Order> orderList, Timestamp latestLeaveTime, long backDuration, long sumDeliveryDuration) {
        this.maxDeliveryNumEachDriver = maxDeliveryNumEachDriver;
        this.id = id;
        this.shopId = shopId;
        this.name = name;
        this.phone = phone;
        this.state = state;
        this.orderList = orderList;
        this.latestLeaveTime = latestLeaveTime;
        this.backDuration = backDuration;
        this.sumDeliveryDuration = sumDeliveryDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DriverStatus getState() {
        return state;
    }

    public void setState(DriverStatus state) {
        this.state = state;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Timestamp getLatestLeaveTime() {
        return latestLeaveTime;
    }

    public void setLatestLeaveTime(Timestamp latestLeaveTime) {
        this.latestLeaveTime = latestLeaveTime;
    }

    public long getBackDuration() {
        return backDuration;
    }

    public void setBackDuration(long backDuration) {
        this.backDuration = backDuration;
    }
    public long getSumDeliveryDuration() {
        return sumDeliveryDuration;
    }

    public void setSumDeliveryDuration(long sumDeliveryDuration) {
        this.sumDeliveryDuration = sumDeliveryDuration;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +" "+
                "orderList=" + new Order().getOrderListInfo(orderList) +
                '}';
    }
}
