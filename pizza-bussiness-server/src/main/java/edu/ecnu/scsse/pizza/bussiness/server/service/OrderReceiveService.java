package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.*;
import edu.ecnu.scsse.pizza.bussiness.server.model.gaode.GaoDeMapUtil;
import edu.ecnu.scsse.pizza.data.domain.*;
import edu.ecnu.scsse.pizza.data.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class OrderReceiveService {
    private static final Logger log = LoggerFactory.getLogger(OrderReceiveService.class);
    private static final int max_duration = 60*25;

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


    public OrderReceiveResponse getReceiveShopId(OrderReceiveRequest orderReceiveRequest)
    {
        //请求参数
        String orderUuid = orderReceiveRequest.getOrderUuid();
        int addressUserId = orderReceiveRequest.getUserAddressId();
        OrderReceiveResponse orderReceiveResponse=new OrderReceiveResponse();

        Optional<UserAddressEntity> userAddressEntity = userAddressJpaRepository.findById(addressUserId);
        Optional<OrderEntity> orderEntity= orderJpaRepository.findByOrderUuid(orderUuid);

        Order subOrder = new Order();

        //组装order的原料清单
        if(orderEntity.isPresent()){
            OrderEntity order = orderEntity.get();
            int orderId = order.getId();
            subOrder.setOrderId(String.valueOf(orderId));
            Timestamp commitTime = order.getCommitTime();
            subOrder.setCommitTime(String.valueOf(commitTime));
            List<OrderMenuEntity> orderMenuEntityList= orderMenuJpaRepository.findByOrderId(orderId);
            List<Menu> menuList = new ArrayList<>();
            for(OrderMenuEntity orderMenuEntity:orderMenuEntityList){
                Menu menu =new Menu();
                menu.setCount(orderMenuEntity.getCount());
                menu.setId(String.valueOf(orderMenuEntity.getMenuId()));
                List<MenuIngredientEntity> menuIngredientEntityList = menuIngredientJpaRepository.findByMenuId(orderMenuEntity.getMenuId());
                Map<Ingredient,Integer> ingredientIntegerMap=new HashMap<Ingredient,Integer>();
                for(MenuIngredientEntity menuIngredientEntity:menuIngredientEntityList){
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(menuIngredientEntity.getIngredientId());
                    ingredientIntegerMap.put(ingredient,menuIngredientEntity.getCount());
                }
                menu.setIngredientIntegerMap(ingredientIntegerMap);
                menuList.add(menu);
            }
        }
        //组装全部的shop集合
        List<PizzaShopEntity> pizzaShopEntityList = pizzaShopJpaRepository.findAll();
        List<Shop> shopList= new ArrayList<>();
        for(PizzaShopEntity pizzaShopEntity:pizzaShopEntityList){
            Shop shop= new Shop();
            shop.setStartTime(pizzaShopEntity.getStartTime());
            shop.setEndTime(pizzaShopEntity.getEndTime());
            shop.setId(pizzaShopEntity.getId());
            shop.setMaxNum(pizzaShopEntity.getMaxNum());
            shop.setMapPoint(new MapPoint(pizzaShopEntity.getLat().doubleValue(),pizzaShopEntity.getLon().doubleValue()));
            shopList.add(shop);
        }

        //拿到终点的距离
        if (userAddressEntity.isPresent()){
            orderReceiveResponse =new OrderReceiveResponse();
            UserAddressEntity userAddress = userAddressEntity.get();
            int addressId = userAddress.getAddressId();
            Optional<AddressEntity> addressEntity = addressJpaRepository.findById(addressId);
            if (addressEntity.isPresent()){
                AddressEntity address = addressEntity.get();
                double lat = address.getLat().doubleValue();
                double lon = address.getLon().doubleValue();
                Point destination = new MapPoint(lat,lon);
                GaoDeMapUtil gaoDeMapUtil=new GaoDeMapUtil();
                Map<Shop,Double> shopDurationMap= new HashMap<>();
                for(Shop shop:shopList){
                    double duration = gaoDeMapUtil.driveRoutePlan(shop.getMapPoint(),destination).total_duation();
                    boolean durationBool=(int)duration < max_duration;
                    boolean commitTimeBool = Timestamp.valueOf(subOrder.getCommitTime()).after(shop.getStartTime()) && Timestamp.valueOf(subOrder.getCommitTime()).before(shop.getEndTime());
                    if(durationBool && commitTimeBool){
                        shopDurationMap.put(shop,duration);
                    }
                }
                if(shopDurationMap.size()>0){
                    List<Map.Entry<Shop,Double>> tmpList = new ArrayList<Map.Entry<Shop,Double>>(shopDurationMap.entrySet());
                    Collections.sort(tmpList, new Comparator<Map.Entry<Shop,Double>>() {
                        public int compare(Map.Entry<Shop,Double> o1, Map.Entry<Shop,Double> o2) {
                            return new Double(o2.getValue() - o1.getValue()).intValue();
                        }
                    });
                    getSuitableShopId(tmpList,subOrder);
                }
            }
        }

        return orderReceiveResponse;
    }

    public int getSuitableShopId(List<Map.Entry<Shop,Double>> shopList,Order order){

        return 1;
    }

}



