package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.model.entity.*;
import edu.ecnu.scsse.pizza.data.domain.DriverEntity;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import edu.ecnu.scsse.pizza.data.domain.PizzaShopEntity;
import edu.ecnu.scsse.pizza.data.enums.DriverStatus;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.repository.DriverJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.OrderJpaRepository;
import edu.ecnu.scsse.pizza.data.repository.PizzaShopJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class DeliveryService {
    private static final Logger log = LoggerFactory.getLogger(OrderReceiveService.class);
    @Autowired
    private DriverJpaRepository driverJpaRepository;
    @Autowired
    private PizzaShopJpaRepository pizzaShopJpaRepository;
    @Autowired
    private OrderJpaRepository orderJpaRepository;

    private List<Driver> driverList;
    private Map<Integer,Timer> driverTimerMap;

    private PriorityQueue<Order> waitToDeliveryOrderQueue;

    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    private static long millisecondNumToOneSecond=1000;


    public synchronized void deliveryOrder(Order waitToDeliveryOrder){
        //获得订单相关的信息

        int shopId = Integer.valueOf(waitToDeliveryOrder.getShopId());
        Optional<PizzaShopEntity> opPizzaShopEntity=pizzaShopJpaRepository.findById(shopId);
        double shopLat=0;
        double shopLon=0;
        if(opPizzaShopEntity.isPresent()){
            PizzaShopEntity pizzaShopEntity = opPizzaShopEntity.get();
            shopLat=pizzaShopEntity.getLat().doubleValue();
            shopLon=pizzaShopEntity.getLon().doubleValue();
            Point shopMapPoint = new MapPoint(shopLat,shopLon);

            log.info("商家"+shopId+"正在处理订单"+waitToDeliveryOrder.getOrderId());

            //这里会查询一遍driver表格，处理是否有新司机加入
            List<DriverEntity> driverEntityList=driverJpaRepository.findAll();
            for(DriverEntity driverEntity:driverEntityList){
                boolean newDriver=true;
                for (Driver driver:driverList){
                    if (driver.getId()==driverEntity.getId()){
                        newDriver=false;
                        break;
                    }
                }
                if(newDriver){
                    Driver driver=new Driver();
                    driver.setId(driverEntity.getId());
                    driver.setShopId(driverEntity.getShopId());
                    driver.setState(DriverStatus.fromDbValue(driverEntity.getState()));
                    List<Order> orderList=new ArrayList<>();
                    driver.setOrderList(orderList);
                    driverList.add(driver);

                    //除了加入driverList，还要处理加入driverTimerMap的新Timer
                    driverTimerMap.put(driver.getId(),new Timer());
                }
            }

            //从本地内存拿这家店对应的driver后做查询分配给该骑手的最优路线
            Map<Driver,DeliverySchedule> maps = new HashMap<>();
            for(Driver driver:driverList){
                if(driver.getShopId()==shopId){
                    maps.put(driver,driver.getDelieveScoreByAddNewOrder(waitToDeliveryOrder,shopMapPoint));
                }
            }
            boolean allDriverFail = true;
            List<DeliverySchedule> deliveryScheduleList = new ArrayList<>();
            for(Map.Entry<Driver,DeliverySchedule> entry:maps.entrySet()){
                DeliverySchedule deliverySchedule= entry.getValue();
                if(deliverySchedule.isAbleToDelivery()){
                    deliveryScheduleList.add(deliverySchedule);
                    allDriverFail=false;
                }
            }

            if(!allDriverFail){
                Collections.sort(deliveryScheduleList);
                boolean assignResult =false;
                for(DeliverySchedule deliverySchedule:deliveryScheduleList){
                    log.info("候选配送路线:"+deliverySchedule.toString());
                    Driver driver= new Driver();
                    for(Map.Entry<Driver,DeliverySchedule> entry:maps.entrySet()){
                        if(entry.getKey().getId()==deliverySchedule.getDriverId()){
                            driver=entry.getKey();
                            break;
                        }
                    }
                    if(checkDriverState(driver,deliverySchedule,waitToDeliveryOrder)){
                        log.info("骑手"+driver.getId()+"配送订单"+waitToDeliveryOrder.getOrderId()+"的路线为"+deliverySchedule.toString());
                        assignResult=true;
                        break;
                    }
                }
                if(!assignResult){
                    log.info("所有骑手均不能配送:全部超过配送的最大数量或全部骑手都在忙碌中或其他原因");
                    waitToDeliveryOrderQueue.add(waitToDeliveryOrder);
                }
            }else {
                waitToDeliveryOrderQueue.add(waitToDeliveryOrder);
                log.info("所有骑手均不能配送:全部超过配送的最大数量或全部骑手都在忙碌中");
            }
        }else {
            log.info("shopId:"+shopId+"查询失败");
        }
    }

    @Transactional
    public boolean checkDriverState(Driver driver,DeliverySchedule deliverySchedule,Order waitToDeliveryOrder){
        //select for update 避免骑手的Timer这个时候改变骑手状态
        List<DriverEntity> driverList=driverJpaRepository.findDriverByIdForUpdate(driver.getId());
        //可能这个时候删除了骑手，但maps还不知道
        if(driverList.size()==1){
            //骑手状态为空闲
            if(driverList.get(0).getState()==DriverStatus.LEISURE.getDbValue()){
                //由于没有已分配骑手未出发这个状态，所以只是修改了order id 对应的driver id
                int updateDriverResult=orderJpaRepository.updateStateAndDriverIdByOrderId(OrderStatus.WAIT_DELIVERY.getDbValue(),driver.getId(), Integer.valueOf(waitToDeliveryOrder.getOrderId()));
                if(updateDriverResult==1){
                    log.info("更新orderId:"+waitToDeliveryOrder.getOrderId()+"的骑手Id"+driver.getId());
                    driver.setLatestLeaveTime(deliverySchedule.getMinLatestLeaveTime());
                    driver.getOrderList().add(waitToDeliveryOrder);
                    for(Order order:driver.getOrderList()){
                        order.setDeliveryDuration(deliverySchedule.getDeliveryTimeEachOrderId().get(order.getOrderId()));
                    }
                    driver.setBackDuration(deliverySchedule.getBackDuration());
                    driver.setSumDeliveryDuration(deliverySchedule.getSumDeliveryDuration());
                    log.info("分配orderId"+waitToDeliveryOrder.getOrderId()+"后骑手的最新状态为"+driver.toString());
                    if(driver.getOrderList().size()==1){
                        log.info("骑手"+driver.getId()+"初次设置定时器");
                        Timer nTimer=new Timer();
                        TimerTask task= new TimerTask(){
                            @Override
                            public void run() {
                                if(driver.getLatestLeaveTime().before(new Timestamp(System.currentTimeMillis()))){
                                    nTimer.cancel();
                                    int updateResult=driverJpaRepository.updateDriverStateById(DriverStatus.BUSY.getDbValue(),driver.getId());
                                    if(updateResult ==1){
                                        driver.setState(DriverStatus.BUSY);
                                        //线程池
                                        Timestamp now=new Timestamp(System.currentTimeMillis());
                                        log.info(now.toString()+":骑手"+driver.getId()+"离开店，更改状态为忙碌");
                                        List<ScheduledThreadPoolExecutor> scheduledThreadPoolExecutorList=new ArrayList<>();
                                        for(Order order:driver.getOrderList()){
                                            ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
                                            scheduledThreadPoolExecutorList.add(schedule);
                                            int orderUpdateResult=orderJpaRepository.updateStateAndStartDeliveryTimeByOrderId(OrderStatus.DELIVERING.getDbValue(),now, Integer.valueOf(order.getOrderId()));
                                            if(orderUpdateResult==1){
                                                log.info(now.toString()+":订单"+order.getOrderId()+"更改状态为配送中");
                                                //设置订单的接收时间
                                                schedule.schedule(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        int orderUpdateResult1=orderJpaRepository.updateStateAndEndDeliveryTimeByOrderId(OrderStatus.RECEIVED.getDbValue(),new Timestamp(now.getTime()+order.getDeliveryDuration()),Integer.valueOf(order.getOrderId()));
                                                        if(orderUpdateResult1==1){
                                                            log.info(new Timestamp(now.getTime()+order.getDeliveryDuration()).toString()+":订单"+order.getOrderId()+"更改状态为已送达");
                                                        }else {
                                                            log.info("失败！"+new Timestamp(now.getTime()+order.getDeliveryDuration()).toString()+"时，订单"+order.getOrderId()+"更改状态为已送达失败");
                                                        }
                                                    }
                                                },order.getDeliveryDuration(), TimeUnit.MILLISECONDS);
                                            }
                                            else {
                                                log.info("失败！"+now.toString()+"时，订单"+order.getOrderId()+"更改状态为配送中，失败");
                                            }
                                        }
                                        ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
                                        scheduledThreadPoolExecutorList.add(schedule);
                                        schedule.schedule(new Runnable() {
                                            @Override
                                            public void run() {
                                                int driverUpdateResult=driverJpaRepository.updateDriverStateById(DriverStatus.LEISURE.getDbValue(),driver.getId());
                                                if(driverUpdateResult==1){
                                                    List<Order> orders=new ArrayList<>();
                                                    driver.setOrderList(orders);
                                                    driver.setSumDeliveryDuration(0);
                                                    driver.setLatestLeaveTime(null);
                                                    driver.setBackDuration(0);
                                                    driver.setState(DriverStatus.LEISURE);
                                                    log.info(new Timestamp(System.currentTimeMillis()).toString()+" 骑手"+driver.getId()+"已回店");
                                                }
                                            }
                                        },driver.getBackDuration()+driver.getSumDeliveryDuration(),TimeUnit.MILLISECONDS);

                                    }

                                }
                            }
                        };
                        driverTimerMap.put(driver.getId(),nTimer);
                        driverTimerMap.get(driver.getId()).schedule(task,1000,1000);
                    }
                }
                return true;
            }
        }
        log.info("数据库查询不到driver"+driver.getId());
        return false;
    }


    public void setScheduledThreadPoolExecutor(){
        scheduledThreadPoolExecutor=new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if(!waitToDeliveryOrderQueue.isEmpty()){
                    Order order=waitToDeliveryOrderQueue.poll();
                    log.info("订单"+order.getOrderId()+"从等待配送队列中取出再次进行分配");
                    deliveryOrder(order);
                }
            }
        },60*millisecondNumToOneSecond,60*millisecondNumToOneSecond, TimeUnit.MILLISECONDS);
    }

    public void setWaitToDeliveryOrderQueue(PriorityQueue<Order> waitToDeliveryOrderQueue) {
        this.waitToDeliveryOrderQueue = waitToDeliveryOrderQueue;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    public void setDriverTimerMap(Map<Integer, Timer> driverTimerMap) {
        this.driverTimerMap = driverTimerMap;
    }

}
