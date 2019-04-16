package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OrderState;
import edu.ecnu.scsse.pizza.bussiness.server.model.gaode.BicyclingData;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.*;
import edu.ecnu.scsse.pizza.bussiness.server.model.gaode.GaoDeMapUtil;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class OrderReceiveService {
    private static final Logger log = LoggerFactory.getLogger(OrderReceiveService.class);
    private static final int max_duration = 60*25;
    private static long millisecondNumToOneSecond=1000;

    private Map<Integer,Integer> shopOrderNumMap;


    @Autowired
    private PizzaShopJpaRepository pizzaShopJpaRepository;
    @Autowired
    private UserAddressJpaRepository userAddressJpaRepository;
    @Autowired
    private AddressJpaRepository addressJpaRepository;
    @Autowired
    private ShopIngredientJpaRepository shopIngredientJpaRepository;
    @Autowired
    private IngredientJpaRepository ingredientJpaRepository;
    @Autowired
    private OrderMenuJpaRepository orderMenuJpaRepository;
    @Autowired
    private OrderJpaRepository orderJpaRepository;
    @Autowired
    private MenuIngredientJpaRepository menuIngredientJpaRepository;
    @Autowired
    private DeliveryService deliveryService;


    public OrderReceiveResponse getReceiveShopId(OrderReceiveRequest orderReceiveRequest)
    {
        //请求参数
        String orderUuid = orderReceiveRequest.getOrderUuid();
        int addressUserId = orderReceiveRequest.getUserAddressId();
        OrderReceiveResponse orderReceiveResponse=new OrderReceiveResponse();

        //通过user_address_id查UserAddress(AddressId)
        Optional<UserAddressEntity> userAddressEntity = userAddressJpaRepository.findById(addressUserId);
        //通过orderUuid查Order
        Optional<OrderEntity> orderEntity= orderJpaRepository.findByOrderUuid(orderUuid);
        //组装待处理的订单
        Order aimOrder = new Order();
        //组装可供之后计算的aimOrder的信息，主要是原料清单
        if(orderEntity.isPresent()) {//数据库里找不到这个orderUuid
            OrderEntity order = orderEntity.get();
            int orderId = order.getId();
            aimOrder.setOrderId(String.valueOf(orderId));
            aimOrder.setOrderUuid(orderUuid);
            Timestamp commitTime = new Timestamp(System.currentTimeMillis());
            aimOrder.setCommitTime(String.valueOf(commitTime));
            order.setCommitTime(commitTime);
            //写入提交时间
            aimOrder.setLatestReceiveTime(commitTime.getTime()+max_duration*millisecondNumToOneSecond);
            List<OrderMenuEntity> orderMenuEntityList = orderMenuJpaRepository.findByOrderId(orderId);
            List<Menu> menuList = new ArrayList<>();
            for (OrderMenuEntity orderMenuEntity : orderMenuEntityList) {
                Menu menu = new Menu();
                menu.setCount(orderMenuEntity.getCount());
                menu.setId(String.valueOf(orderMenuEntity.getMenuId()));
                List<MenuIngredientEntity> menuIngredientEntityList = menuIngredientJpaRepository.findByMenuId(orderMenuEntity.getMenuId());
                List<Ingredient> ingredientList = new ArrayList<>();
                for (MenuIngredientEntity menuIngredientEntity : menuIngredientEntityList) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(menuIngredientEntity.getIngredientId());
                    ingredient.setCount(menuIngredientEntity.getCount());
                    ingredientList.add(ingredient);
                }
                menu.setIngredients(ingredientList);
                menuList.add(menu);
            }
            aimOrder.setMenuList(menuList);

            //组装全部的shop
            List<PizzaShopEntity> pizzaShopEntityList = pizzaShopJpaRepository.findAll();
            List<Shop> shopList = new ArrayList<>();
            for (PizzaShopEntity pizzaShopEntity : pizzaShopEntityList) {
                Shop shop = new Shop();
                shop.setStartTime(pizzaShopEntity.getStartTime());
                shop.setEndTime(pizzaShopEntity.getEndTime());
                shop.setId(pizzaShopEntity.getId());
                shop.setMaxNum(pizzaShopEntity.getMaxNum());
                shop.setMapPoint(new MapPoint(pizzaShopEntity.getLat().doubleValue(), pizzaShopEntity.getLon().doubleValue()));
                shopList.add(shop);
            }

            if(pizzaShopEntityList.size()==0){
                orderReceiveResponse.setResultType(ResultType.FAILURE);
                orderReceiveResponse.setOrderEntity(order);
                orderReceiveResponse.setErrorMsg("无法查询到全部的pizza_shop");
            }else{
                //拿到终点的距离
                if (userAddressEntity.isPresent()) {
                    orderReceiveResponse = new OrderReceiveResponse();
                    UserAddressEntity userAddress = userAddressEntity.get();
                    int addressId = userAddress.getAddressId();
                    order.setAddressId(addressId);
                    Optional<AddressEntity> addressEntity = addressJpaRepository.findById(addressId);
                    if (addressEntity.isPresent()) {
                        AddressEntity address = addressEntity.get();
                        double lat = address.getLat().doubleValue();
                        double lon = address.getLon().doubleValue();

                        boolean isAnythingWrong=false;
                        String errorMsg="";

                        Point destination = new MapPoint(lat, lon);
                        aimOrder.setMapPoint(destination);
                        GaoDeMapUtil gaoDeMapUtil = new GaoDeMapUtil();
                        Map<Shop, Double> shopDurationMap = new HashMap<>();
                        for (Shop shop : shopList) {
                            BicyclingData bicyclingData=gaoDeMapUtil.driveRoutePlan(shop.getMapPoint(), destination);

                            if (bicyclingData.getErrcode()==0){
                                double duration = gaoDeMapUtil.driveRoutePlan(shop.getMapPoint(), destination).total_duation();
                                boolean durationBool = (int) duration < max_duration;
                                boolean commitTimeBool = Timestamp.valueOf(aimOrder.getCommitTime()).after(shop.getStartTime()) && Timestamp.valueOf(aimOrder.getCommitTime()).before(shop.getEndTime());
                                if (durationBool && commitTimeBool) {
                                    shopDurationMap.put(shop, duration);
                                }
                            }else {
                                isAnythingWrong=true;
                                if(bicyclingData.getErrmsg()!=null){
                                    errorMsg=bicyclingData.getErrmsg();
                                }else if(bicyclingData.getErrdetail()!=null){
                                    errorMsg+=bicyclingData.getErrdetail();
                                }
                                break;
                            }
                        }
                        if(!isAnythingWrong){
                            if (shopDurationMap.size() > 0) {
                                List<Map.Entry<Shop, Double>> tmpList = new ArrayList<Map.Entry<Shop, Double>>(shopDurationMap.entrySet());
                                Collections.sort(tmpList, new Comparator<Map.Entry<Shop, Double>>() {
                                    public int compare(Map.Entry<Shop, Double> o1, Map.Entry<Shop, Double> o2) {
                                        return new Double(o1.getValue() - o2.getValue()).intValue();
                                    }
                                });
                                orderReceiveResponse = getSuitableShopId(tmpList, aimOrder, orderEntity.get());
                                Thread thread=new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        log.info("orderID:"+aimOrder.getOrderId()+"已被分配到配送服务");
                                        deliveryService.deliveryOrder(aimOrder);
                                    }
                                });
                                thread.start();
                                return orderReceiveResponse;
                            } else {
                                int result= orderJpaRepository.updateStateCommitTimeAddressIdByOrderUuid(OrderStatus.RECEIVE_FAIL.getDbValue(),order.getCommitTime(),addressId,order.getOrderUuid());
                                if(result==1){
                                    order.setState(OrderStatus.RECEIVE_FAIL.getDbValue());
                                    orderReceiveResponse.setOrderEntity(order);
                                    orderReceiveResponse.setSuccessMsg("全部门店距离超配送范围");
                                }else {
                                    orderReceiveResponse.setResultType(ResultType.FAILURE);
                                    orderReceiveResponse.setOrderEntity(order);
                                    orderReceiveResponse.setErrorMsg("全部门店距离超配送范围,更改order表中state为RECEIVE_FAIL，失败");
                                }
                            }
                        }
                        else {
                            int result= orderJpaRepository.updateStateCommitTimeAddressIdByOrderUuid(OrderStatus.RECEIVE_FAIL.getDbValue(),order.getCommitTime(),addressId,order.getOrderUuid());
                            orderReceiveResponse.setOrderEntity(order);
                            orderReceiveResponse.setResultType(ResultType.FAILURE);
                            orderReceiveResponse.setErrorMsg(errorMsg);
                            return orderReceiveResponse;
                        }
                    }else{
                        int result= orderJpaRepository.updateStateCommitTimeByOrderUuid(OrderStatus.RECEIVE_FAIL.getDbValue(),order.getCommitTime(),order.getOrderUuid());
                        orderReceiveResponse.setOrderEntity(order);
                        orderReceiveResponse.setResultType(ResultType.FAILURE);
                        orderReceiveResponse.setErrorMsg("address表中无法查询到id");
                    }
                }else {
                    int result= orderJpaRepository.updateStateCommitTimeByOrderUuid(OrderStatus.RECEIVE_FAIL.getDbValue(),order.getCommitTime(),order.getOrderUuid());
                    orderReceiveResponse.setOrderEntity(order);
                    orderReceiveResponse.setResultType(ResultType.FAILURE);
                    orderReceiveResponse.setErrorMsg("user_address表中无法查询到id");
                }
            }
//            else {
//                orderReceiveResponse.setOrderEntity(order);
//                orderReceiveResponse.setResultType(ResultType.FAILURE);
//                orderReceiveResponse.setErrorMsg("pizza_order写入提交时间失败");
//            }
        }
        else {
            orderReceiveResponse.setOrderEntity(null);
            orderReceiveResponse.setResultType(ResultType.FAILURE);
            orderReceiveResponse.setErrorMsg("pizza_order表无法查询到order_uuid");
        }
        return orderReceiveResponse;
    }

    @Transactional
    public synchronized OrderReceiveResponse getSuitableShopId(List<Map.Entry<Shop,Double>> shopList,Order order,OrderEntity orderEntity){
        OrderReceiveResponse orderReceiveResponse=new OrderReceiveResponse();
        Map<Integer,Integer> orderIngredientNumMap = order.getOrderIngredientMap();
        Shop finalShop =null;

        for(Map.Entry<Shop,Double> shopDoubleEntry:shopList){
            boolean ingredientIsEnoughBool=true;
            boolean supassOrEqualMaxnum;
            int shopId = shopDoubleEntry.getKey().getId();
            Shop shop = shopDoubleEntry.getKey();

            if(shopOrderNumMap.containsKey(shopId)){//处理突然添加了一个shop，shopOrderNumMap里面是没有这样的key的
                supassOrEqualMaxnum= (shopOrderNumMap.get(shopId).intValue()>=shopDoubleEntry.getKey().getMaxNum());
                if (! supassOrEqualMaxnum){
                    List<ShopIngredientEntity> shopIngredientEntityList=shopIngredientJpaRepository.findByShopIdForUpdate(shopId);
                    List<Ingredient> shopIngredientList=new ArrayList<>();
                    for(ShopIngredientEntity shopIngredientEntity:shopIngredientEntityList){
                        Ingredient ingredient=new Ingredient();
                        ingredient.setId(shopIngredientEntity.getIngredientId());
                        ingredient.setCount(shopIngredientEntity.getCount());
                        shopIngredientList.add(ingredient);
                    }
                    shop.setIngredientList(shopIngredientList);
                    for(Map.Entry<Integer,Integer> item:orderIngredientNumMap.entrySet()){
                        int orderIngredientId = item.getKey();
                        int orderIngredientNum = item.getValue();
                        for(Ingredient shopIngredient:shopIngredientList){
                            if(shopIngredient.getId()==orderIngredientId){
                                if(orderIngredientNum>shopIngredient.getCount()){
                                    ingredientIsEnoughBool=false;
                                    break;
                                }
                            }
                        }
                    }
                    if(ingredientIsEnoughBool){
                        finalShop= shop;
                        break;
                    }
                }
            }else {
                shopOrderNumMap.put(shopId,0);
            }

        }
        if(finalShop == null){
            int result= orderJpaRepository.updateStateCommitTimeAddressIdByOrderUuid(OrderStatus.RECEIVE_FAIL.getDbValue(),orderEntity.getCommitTime(),orderEntity.getAddressId(),order.getOrderUuid());
            if(result == 0){
                orderReceiveResponse.setOrderEntity(orderEntity);
                orderReceiveResponse.setResultType(ResultType.FAILURE);
                orderReceiveResponse.setErrorMsg("数据库更新订单状态失败");
            }else {
                orderEntity.setState(OrderStatus.RECEIVE_FAIL.getDbValue());
                orderReceiveResponse.setOrderEntity(orderEntity);
                orderReceiveResponse.setSuccessMsg("无符合菜单原料要求的店铺或已达每日最大接单量");
            }
            return orderReceiveResponse;
        }else {
            int orderResult = orderJpaRepository.updateStateShopIdCommitTimeAdressIdByOrderUuid(OrderStatus.WAIT_DELIVERY.getDbValue(),finalShop.getId(),orderEntity.getCommitTime(),orderEntity.getAddressId(),order.getOrderUuid());
            if(orderResult==1){
                for(Map.Entry<Integer,Integer> item:orderIngredientNumMap.entrySet()) {
                    int orderIngredientId = item.getKey();
                    int orderIngredientNum = item.getValue();
                    for(Ingredient shopIngredient:finalShop.getIngredientList()){
                        if(shopIngredient.getId()==orderIngredientId){
                            int updateNum= shopIngredient.getCount()- orderIngredientNum;
                            int ingredientResult = shopIngredientJpaRepository.updateCountByShopIdAndIngredientId(updateNum,finalShop.getId(),orderIngredientId);
                            if(ingredientResult!=1){
                                orderReceiveResponse.setResultType(ResultType.FAILURE);
                                orderReceiveResponse.setErrorMsg("更新shopID="+finalShop.getId()+",ingredientId="+orderIngredientId+"失败");
                                return orderReceiveResponse;
                            }
                        }
                    }
                }
                shopOrderNumMap.put(finalShop.getId(),shopOrderNumMap.get(finalShop.getId())+1);
                orderEntity.setState(OrderStatus.WAIT_DELIVERY.getDbValue());
                orderEntity.setShopId(finalShop.getId());
                order.setShopId(String.valueOf(finalShop.getId()));
                orderReceiveResponse.setOrderEntity(orderEntity);
                orderReceiveResponse.setSuccessMsg("订单状态成功更新为待配送并已分配到店");
            }else {
                orderReceiveResponse.setResultType(ResultType.FAILURE);
                orderReceiveResponse.setErrorMsg("更新订单状态为待配送失败");
            }
            return orderReceiveResponse;
        }
    }

    //定时任务每天零点清空一次数量
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearshopOrderNumMapOnEachDay(){
        Map<Integer,Integer> shopOrderNumMap= new HashMap<>();
        List<PizzaShopEntity> pizzaShopEntityList = pizzaShopJpaRepository.findAll();
        for(PizzaShopEntity pizzaShopEntity:pizzaShopEntityList){
            shopOrderNumMap.put(pizzaShopEntity.getId(),0);
        }
        List<OrderEntity> orderEntityList = orderJpaRepository.findAllOrderCommitToday();
        for(OrderEntity orderEntity:orderEntityList){
            if (orderEntity.getShopId() != null){
                shopOrderNumMap.put(orderEntity.getShopId(),shopOrderNumMap.get(orderEntity.getShopId())+1);
            }
        }
        this.shopOrderNumMap = shopOrderNumMap;
        log.info("零点重新组装shopOrderNumMap:"+shopOrderNumMap.toString());
    }

    public void setShopOrderNumMap(Map<Integer, Integer> shopOrderNumMap) {
        this.shopOrderNumMap = shopOrderNumMap;
    }

}



