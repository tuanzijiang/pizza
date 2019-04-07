package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.FakeAdmin;
import edu.ecnu.scsse.pizza.bussiness.server.FakeFactory;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;
import edu.ecnu.scsse.pizza.bussiness.server.model.gaode.BicyclingData;
import edu.ecnu.scsse.pizza.bussiness.server.model.gaode.GaoDeMapUtil;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveRequest;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.OrderReceiveResponse;
import edu.ecnu.scsse.pizza.data.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderReceiveServiceTest extends TestApplication{
    @Mock
    private UserAddressJpaRepository userAddressJpaRepository;

    @Mock
    private OrderMenuJpaRepository orderMenuJpaRepository;

    @Mock
    private OrderJpaRepository orderJpaRepository;

    @Mock
    private PizzaShopJpaRepository shopJpaRepository;

    @Mock
    private MenuIngredientJpaRepository menuIngredientJpaRepository;

    @Mock
    private AddressJpaRepository addressJpaRepository;

    @Mock
    private ShopIngredientJpaRepository shopIngredientJpaRepository;

    @Mock
    @Autowired
    private DeliveryService deliveryService;

    @Mock
    private GaoDeMapUtil gaoDeMapUtil;

    @Mock
    private BicyclingData bicyclingData;

    @InjectMocks
    private OrderReceiveService orderReceiveService;

    @Before
    public void setUp(){

    }

    @Test
    public void testGetReceiveShopIdWithWrongOrderId(){
        String orderUuid = "AAA";
        int userAddressId = 1;
        OrderReceiveRequest request = new OrderReceiveRequest(orderUuid, userAddressId);
        when(userAddressJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUserAddress(1,1)));
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(Optional.empty());
        OrderReceiveResponse response = orderReceiveService.getReceiveShopId(request);
        assertEquals(ResultType.FAILURE,response.getResultType());
    }

    @Test
    public void testGetReceiveShopIdWithNoShopsAvailable(){
        String orderUuid = "AAA";
        int userAddressId = 1;
        OrderReceiveRequest request = new OrderReceiveRequest(orderUuid, userAddressId);
        when(userAddressJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUserAddress(1,1)));
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(Optional.of(FakeFactory.fakeOrder(orderUuid)));
        when(orderMenuJpaRepository.findByOrderId(anyInt())).thenReturn(FakeFactory.fakeOrderMenuEntities(1));
        when(menuIngredientJpaRepository.findByMenuId(anyInt())).thenReturn(FakeFactory.fakeMenuIngredientEntities(1));
        when(shopJpaRepository.findAll()).thenReturn(anyList());

        OrderReceiveResponse response = orderReceiveService.getReceiveShopId(request);
        assertEquals(ResultType.FAILURE,response.getResultType());
        assertEquals("无法查询到全部的pizza_shop",response.getErrorMsg());
    }

    @Test
    public void testGetReceiveShopIdWithoutAddressEntity(){
        String orderUuid = "AAA";
        int userAddressId = 1;
        OrderReceiveRequest request = new OrderReceiveRequest(orderUuid, userAddressId);
        when(userAddressJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUserAddress(1,1)));
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(Optional.of(FakeFactory.fakeOrder(orderUuid)));
        when(orderMenuJpaRepository.findByOrderId(anyInt())).thenReturn(FakeFactory.fakeOrderMenuEntities(1));
        when(menuIngredientJpaRepository.findByMenuId(anyInt())).thenReturn(FakeFactory.fakeMenuIngredientEntities(1));
        when(shopJpaRepository.findAll()).thenReturn(FakeFactory.fakeShops());
        when(addressJpaRepository.findById(anyInt())).thenReturn(Optional.empty());

        OrderReceiveResponse response = orderReceiveService.getReceiveShopId(request);
        assertEquals(ResultType.FAILURE,response.getResultType());
        assertEquals("address表中无法查询到id",response.getErrorMsg());
    }

    @Test
    public void testGetReceiveShopIdWithAllDurationGreaterThanMaxDuration(){
        BicyclingData data = new BicyclingData("","",new ArrayList<>(),0,"","");
        double duration = 60*25+1;
        String orderUuid = "AAA";
        int userAddressId = 1;
        OrderReceiveRequest request = new OrderReceiveRequest(orderUuid, userAddressId);
        when(userAddressJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUserAddress(1,1)));
        when(orderJpaRepository.findByOrderUuid(orderUuid)).thenReturn(Optional.of(FakeFactory.fakeOrder(orderUuid)));
        when(orderMenuJpaRepository.findByOrderId(anyInt())).thenReturn(FakeFactory.fakeOrderMenuEntities(1));
        when(menuIngredientJpaRepository.findByMenuId(anyInt())).thenReturn(FakeFactory.fakeMenuIngredientEntities(1));
        when(shopJpaRepository.findAll()).thenReturn(FakeFactory.fakeShops());
        when(addressJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeAddressEntity(1)));
        //when(gaoDeMapUtil.driveRoutePlan(any(),any())).thenReturn(data);
        when(bicyclingData.total_duation()).thenReturn(duration);
        when(orderJpaRepository.updateStateByOrderUuid(eq(11),anyString())).thenReturn(1);

        OrderReceiveResponse response = orderReceiveService.getReceiveShopId(request);

        when(shopIngredientJpaRepository.findByShopIdForUpdate(anyInt())).thenReturn(FakeFactory.fakeShopIngredients(1));

        assertEquals("全部门店距离超配送范围",response.getSuccessMsg());
    }
}
