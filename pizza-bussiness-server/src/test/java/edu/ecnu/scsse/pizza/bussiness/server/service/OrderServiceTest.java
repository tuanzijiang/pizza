package edu.ecnu.scsse.pizza.bussiness.server.service;

import edu.ecnu.scsse.pizza.bussiness.server.FakeAdmin;
import edu.ecnu.scsse.pizza.bussiness.server.FakeFactory;
import edu.ecnu.scsse.pizza.bussiness.server.TestApplication;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Order;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.PendingOrder;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.SaleStatus;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.ResultType;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.SimpleResponse;
import edu.ecnu.scsse.pizza.bussiness.server.model.request_response.order.*;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.repository.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class OrderServiceTest extends TestApplication {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);
    @Mock
    private OrderJpaRepository orderJpaRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserAddressJpaRepository userAddressJpaRepository;

    @Mock
    private PizzaShopJpaRepository pizzaShopJpaRepository;

    @Mock
    private DriverJpaRepository driverJpaRepository;

    @Mock
    private MenuJpaRepository menuJpaRepository;

    @Mock
    private OrderMenuJpaRepository  orderMenuJpaRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testGetOrderList() throws Exception{
        List<OrderEntity> orders = FakeFactory.fakeOrders();
        when(orderJpaRepository.findAll()).thenReturn(orders);
        when(userAddressJpaRepository.findByUserIdAndAddressId(anyInt(),anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUserAddress(1,1)));
        when(orderMenuJpaRepository.findByOrderId(anyInt())).thenReturn(FakeFactory.fakeOrderMenuEntities(1));
        when(menuJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeMenu()));
        when(userJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUser(1)));
        when(pizzaShopJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeShop()));
        when(driverJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeDriver(1)));
        List<OrderManageResponse> orderList = orderService.getOrderList();
        assertEquals(orders.size(),orderList.size());
    }

    @Test
    public void testGetOrderDetailByRightMenuId() throws Exception{
        OrderManageResponse orderDetailResponse = orderService.getOrderDetail(1);
        //OrderDetailResponse exceptedResponse = new OrderDetailResponse()
        assertEquals(1,Integer.parseInt(orderDetailResponse.getOrderId()));
       // Assert.assertEquals(1,orderDetailResponse.getState().getDbValue());
        assertEquals("123",orderDetailResponse.getReceivePhone());
        assertEquals("cao miao",orderDetailResponse.getReceiveName());
        assertEquals("15800349392",orderDetailResponse.getBuyPhone());
        assertEquals(1,Integer.parseInt(orderDetailResponse.getShopId()));
        assertEquals("必胜客",orderDetailResponse.getShopName());
        assertEquals("",orderDetailResponse.getDriverId());
    }

    @Test
    public void testGetOrderDriverInfoByMenuId()throws Exception{
        OrderManageResponse orderDetailResponse = orderService.getOrderDetail(2);
        assertEquals(-1,Integer.parseInt((orderDetailResponse.getDriverId().equals(""))?"-1":orderDetailResponse.getDriverId()));
        assertEquals("13162308625",orderDetailResponse.getDriverPhone());
        assertEquals("cqh",orderDetailResponse.getDriverName());
    }


    @Test
    public void testGetYesterdaySaleWithDate() throws ParseException{
        YesterdaySaleResponse response = orderService.getYesterdaySaleStatus("2019/03/14");
        assertEquals(ResultType.SUCCESS,response.getResultType());
        assertEquals(1,response.getOrderNum());
        assertEquals(0,response.getCancelNum());
        assertEquals(0,response.getCompleteNum());
    }

    @Test
    public void testGetSaleStatusList() throws ParseException,Exception{
        String startDate = "2019/04/08";
        String endDate = "2019/04/09";
        List<List<OrderEntity>> days = new ArrayList<>();
        for(int i=0;i<2;i++) {
            String date = "2019/04/0"+String.valueOf(i+8);
            List<OrderEntity> oneDay = FakeFactory.fakeOrdersByOneDate(date);
            when(orderJpaRepository.findTotalOrdersByCommitTime(date)).thenReturn(5);
            days.add(oneDay);
        }
        List<SaleStatus> saleStatusList = orderService.getSaleStatusList(startDate,endDate);
        assertEquals(days.size(),saleStatusList.size());
    }

    @Test
    public void testGetYesterdaySaleStatus() throws ParseException{
        String date = "2019/04/08";
        List<OrderEntity> oneDay = FakeFactory.fakeOrdersByOneDate(date);
        when(orderJpaRepository.findTotalOrdersByCommitTime(date)).thenReturn(5);
        YesterdaySaleResponse response = orderService.getYesterdaySaleStatus(date);
        assertEquals(response.getOrderNum(),oneDay.size());
    }

    @Test
    public void testGetPendingList(){
        List<OrderEntity> orders = FakeFactory.fakePendingOrders();
        when(orderJpaRepository.findPendingList()).thenReturn(orders);
        when(userAddressJpaRepository.findByUserIdAndAddressId(anyInt(),anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUserAddress(1,1)));
        when(orderMenuJpaRepository.findByOrderId(anyInt())).thenReturn(FakeFactory.fakeOrderMenuEntities(1));
        when(menuJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeMenu()));
        when(userJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUser(1)));
        when(pizzaShopJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeShop()));
        when(driverJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeDriver(1)));
        List<PendingOrder> orderList = orderService.getPendingRequestList();
        assertEquals(orders.size(),orderList.size());
    }

    @Test
    public void testChangeStatusSuccessfully(){
        int orderId = 1;
        int status = 5;
        OrderEntity origin = FakeFactory.fakeCheckingOrder(orderId);
        OrderEntity after = new OrderEntity();
        after.setState(status);
        when(orderJpaRepository.findById(anyInt())).thenReturn(Optional.of(origin));
        when(orderJpaRepository.saveAndFlush(any(OrderEntity.class))).thenReturn(after);
        SimpleResponse response = orderService.changeOrderStatus(orderId,OrderStatus.fromDbValue(status));
        assertEquals(ResultType.SUCCESS,response.getResultType());
    }

    @Test
    public void testChangeStatusFailure(){
        int orderId = 1;
        int status = 5;
        OrderEntity origin = FakeFactory.fakeNotCheckingOrder(orderId);
        when(orderJpaRepository.findById(anyInt())).thenReturn(Optional.of(origin));
        SimpleResponse response = orderService.changeOrderStatus(orderId,OrderStatus.fromDbValue(status));
        assertEquals(ResultType.FAILURE,response.getResultType());
    }

    @Test
    public void testGetCancelOrderList(){
        List<OrderEntity> orders = FakeFactory.fakeCancelOrderList();
        when(orderJpaRepository.findCancelOrderList()).thenReturn(orders);
        when(userAddressJpaRepository.findByUserIdAndAddressId(anyInt(),anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUserAddress(1,1)));
        when(orderMenuJpaRepository.findByOrderId(anyInt())).thenReturn(FakeFactory.fakeOrderMenuEntities(1));
        when(menuJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeMenu()));
        when(userJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeAdmin.fakeUser(1)));
        when(pizzaShopJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeShop()));
        when(driverJpaRepository.findById(anyInt())).thenReturn(Optional.of(FakeFactory.fakeDriver(1)));
        List<PendingOrder> orderList = orderService.getCancelOrderList();
        assertEquals(orders.size(),orderList.size());
    }



}
