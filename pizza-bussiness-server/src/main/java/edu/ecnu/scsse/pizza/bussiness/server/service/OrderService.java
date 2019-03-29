package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.exception.NotFoundException;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Menu;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.SaleStatus;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.BaseResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.utils.CopyUtils;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.*;
import edu.ecnu.scsse.pizza.data.repository.*;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserAddressJpaRepository userAddressJpaRepository;

    @Autowired
    private PizzaShopJpaRepository pizzaShopJpaRepository;

    @Autowired
    private DriverJpaRepository driverJpaRepository;

    @Autowired
    private MenuJpaRepository menuJpaRepository;

    @Autowired
    private OrderMenuJpaRepository  orderMenuJpaRepository;

    public List<Order> getOrderList(){
        List<Order> orderList = new ArrayList<>();
        List<OrderEntity> orderEntityList = orderJpaRepository.findAll();
        if(orderEntityList.size()!=0){
            orderList = orderEntityList.stream().map(this::convertSimple).collect(Collectors.toList());
        }
        else{
            log.warn("Fail to find the order list.");
        }

        return orderList;
    }

    public OrderDetailResponse getOrderDetail(int orderId){
        OrderDetailResponse orderDetailResponse;
        Optional<OrderEntity> orderEntityOptional = orderJpaRepository.findById(orderId);
        if(orderEntityOptional.isPresent()){
            OrderEntity orderEntity = orderEntityOptional.get();
            orderDetailResponse = new OrderDetailResponse(convertDetail(orderEntity));        }
        else{
            NotFoundException e = new NotFoundException("Order detail is not found.");
            orderDetailResponse = new OrderDetailResponse(e);
            log.warn("Fail to find the order detail.", e);
        }
        return orderDetailResponse;
    }

    public YesterdaySaleResponse getYesterdaySaleStatus(String yesterday) throws ParseException{
        SaleResponse saleResponse = getSaleStatusList(yesterday,yesterday);
        List<SaleStatus> saleStatusList = saleResponse.getSaleStatusList();
        if(saleStatusList.size()!=0)
            return new YesterdaySaleResponse(saleResponse.getSaleStatusList().get(0));
        else{
            NotFoundException e = new NotFoundException("Data is not found.");
            log.warn("Fail to find yesterday order data.", e);
            return new YesterdaySaleResponse(e);
        }
    }

    public SaleResponse getSaleStatusList(String startDate,String endDate) throws ParseException {
        List<SaleStatus> saleStatusList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        long end = sdf.parse(endDate).getTime();
        long start = sdf.parse(startDate).getTime();
        int days = (int)((end-start)/(1000 * 60 * 60 * 24))+1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(startDate));
        for(int i=0;i<days;i++){
            String date = sdf.format(calendar.getTime());
            SaleStatus saleStatus = getDaySaleStatus(date);
            //if(saleStatus.getOrderNum()!=0)
            saleStatusList.add(saleStatus);
            calendar.add(Calendar.DATE,+1);
        }
        return new SaleResponse(saleStatusList);
    }

    private SaleStatus getDaySaleStatus(String date){
        List<OrderEntity> orderEntityList = orderJpaRepository.findOrderByCommitTime(date);
        int orderNum = orderEntityList.size();
        int completeNum = 0;
        int cancelNum = 0;
        double totalAmount = 0;
        for(OrderEntity orderEntity : orderEntityList){
            switch (OrderStatus.fromDbValue(orderEntity.getState())){
                case RECEIVED:
                    completeNum++;
                    break;
                case CANCELED:
                    cancelNum++;
                    break;
                default:
                    break;
            }
            if(orderEntity.getTotalPrice()!=null)
                totalAmount += orderEntity.getTotalPrice();
        }
        return new SaleStatus(date,orderNum, completeNum, cancelNum, totalAmount);
    }

    public List<Order> getPendingRequestList(){
        List<Order> pendingList = new ArrayList<>();
        List<OrderEntity> orderEntityList = orderJpaRepository.findPendingList();
        if(orderEntityList.size()!=0){
            pendingList = orderEntityList.stream().map(this::convertSimple).collect(Collectors.toList());
        }
        else{
            log.warn("Fail to find the pending list.");
        }

        return pendingList;
    }

    public BaseResponse changeOrderStatus(int orderId, OrderStatus status){
        OrderDetailResponse response = new OrderDetailResponse();
        String msg;
        Optional<OrderEntity> optional = orderJpaRepository.findById(orderId);
        if(optional.isPresent()){
            OrderEntity orderEntity = optional.get();
            if(OrderStatus.fromDbValue(orderEntity.getState())==OrderStatus.CANCEL_CHECKING){
                orderEntity.setState(status.getDbValue());
                orderJpaRepository.saveAndFlush(orderEntity);
                response.setResultType(ResultType.SUCCESS);
                msg = String.format("Success:change order %d status to %s",orderId, status);
                response.setSuccessMsg(msg);
                log.info(msg);
            }
            else{
                response.setResultType(ResultType.FAILURE);
                msg = "Wrong order status.";
                response.setErrorMsg(msg);
                log.warn(msg);
            }
        }
        else{
            msg = String.format("Order %d is not found.",orderId);
            NotFoundException e = new NotFoundException(msg);
            response = new OrderDetailResponse(e);
            log.warn(msg);
        }
        return response;
    }

    private Order convertSimple(OrderEntity orderEntity){
        Order order = new Order();
        CopyUtils.copyProperties(orderEntity, order);

        order.setState(OrderStatus.fromDbValue(orderEntity.getState()));
        order.setOrderId(String.valueOf(orderEntity.getId()));

        //设置收货人信息
        Optional<UserAddressEntity> userAddressEntityOptional = userAddressJpaRepository.findByUserIdAndAddressId(orderEntity.getUserId(),orderEntity.getAddressId());
        if(userAddressEntityOptional.isPresent()){
            UserAddressEntity userAddressEntity = userAddressEntityOptional.get();
            order.setReceiveName(userAddressEntity.getName());
            order.setReceivePhone(userAddressEntity.getPhone());
            order.setReceiveAddress(userAddressEntity.getAddressDetail());
        }

        //下单时间的格式转换
        String commitTimePattern = "yyyy/MM/dd hh:MM:ss";
        DateFormat df = new SimpleDateFormat(commitTimePattern);
        if(orderEntity.getCommitTime()!=null)
            order.setCommitTime(df.format(orderEntity.getCommitTime()));
        return order;
    }

    private Order convertDetail(OrderEntity orderEntity){
        Order order = convertSimple(orderEntity);
        String commitTimePattern = "yyyy/MM/dd hh:MM:ss";
        DateFormat df = new SimpleDateFormat(commitTimePattern);

        //设置订购的披萨信息
        List<Menu> menuList = new ArrayList<>();
        List<OrderMenuEntity> orderMenuEntityList = orderMenuJpaRepository.findByOrderId(orderEntity.getId());
        for(OrderMenuEntity orderMenuEntity : orderMenuEntityList){
            Menu menu = new Menu();
            menu.setCount(orderMenuEntity.getCount());
            Optional<MenuEntity> menuEntityOptional = menuJpaRepository.findById(orderMenuEntity.getMenuId());
            if(menuEntityOptional.isPresent()){
                MenuEntity menuEntity = menuEntityOptional.get();
                CopyUtils.copyProperties(menuEntity,menu);
                menu.setId(String.valueOf(menuEntity.getId()));
            }
            menuList.add(menu);
        }
        //if(menuList.size()!=0){
        order.setMenuList(menuList);
        //}

        //设置购买人电话
        Optional<UserEntity> userEntityOptional = userJpaRepository.findById(orderEntity.getUserId());
        if(userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            order.setBuyPhone(userEntity.getPhone());
        }

        //设置商家信息
        if(orderEntity.getShopId()!=null){
            order.setShopId(String.valueOf(orderEntity.getShopId()));
            Optional<PizzaShopEntity> pizzaShopEntityOptional = pizzaShopJpaRepository.findById(orderEntity.getShopId());
            if(pizzaShopEntityOptional.isPresent()){
                PizzaShopEntity pizzaShopEntity = pizzaShopEntityOptional.get();
                order.setShopName(pizzaShopEntity.getName());
            }
        }

        //设置配送信息
        if(orderEntity.getDriverId()!=null){
            order.setDriverId(String.valueOf(orderEntity.getDriverId()));
            Optional<DriverEntity> driverEntityOptional = driverJpaRepository.findById(orderEntity.getDriverId());
            if(driverEntityOptional.isPresent()){
                DriverEntity driverEntity = driverEntityOptional.get();
                order.setDriverName(driverEntity.getName());
                order.setDriverPhone(driverEntity.getPhone());
            }
            if(orderEntity.getDeliverStartTime()!=null)
                order.setStartDeliverTime(df.format(orderEntity.getDeliverStartTime()));
            if(orderEntity.getDeliverEndTime()!=null)
                order.setArriveTime(df.format(orderEntity.getDeliverEndTime()));
        }
        return order;
    }
}
