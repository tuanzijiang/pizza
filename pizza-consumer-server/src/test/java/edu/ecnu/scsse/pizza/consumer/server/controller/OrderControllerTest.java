package edu.ecnu.scsse.pizza.consumer.server.controller;

import com.google.gson.Gson;
import edu.ecnu.scsse.pizza.consumer.server.FakeFactory;
import edu.ecnu.scsse.pizza.consumer.server.exception.*;
import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.model.ResultType;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Pizza;
import edu.ecnu.scsse.pizza.consumer.server.model.order.*;
import edu.ecnu.scsse.pizza.consumer.server.service.OrderService;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import edu.ecnu.scsse.pizza.data.enums.PizzaStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {

    private static final Gson GSON = new Gson();

    private static final String ORDER_ID = "AAA";

    private static final Integer USER_ID = 1;

    private static final Integer PIZZA_ID = 1;

    private static final Integer USER_ADDR_ID = 1;

    @Mock
    private OrderService service;
    @InjectMocks
    private OrderController controller;

    private MockMvc mvc;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void fetchOrder() throws Exception {
        Order order = FakeFactory.fakeOrder();
        when(service.fetchOrder(ORDER_ID)).thenReturn(order);
        FetchOrderRequest request = new FetchOrderRequest();
        request.setOrderId(ORDER_ID);
        request.setUserId(USER_ID);

        FetchOrderResponse response = new FetchOrderResponse();
        response.setOrder(order);
        mvc.perform(post("/fetchOrder")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(GSON.toJson(response)));

        verify(service).fetchOrder(ORDER_ID);
    }

    @Test
    public void fetchOrderWithException() throws Exception {
        when(service.fetchOrder(ORDER_ID)).thenThrow(ConsumerServerException.class);
        FetchOrderRequest request = new FetchOrderRequest();
        request.setOrderId(ORDER_ID);
        request.setUserId(USER_ID);

        mvc.perform(post("/fetchOrder")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk());

        verify(service).fetchOrder(ORDER_ID);
    }

    @Test
    public void fetchOrders() throws Exception {
        List<Order> orders = FakeFactory.fakeOrders();
        when(service.fetchOrders(anyInt(), anyList(), anyString(), anyInt())).thenReturn(orders);
        FetchOrdersRequest request = new FetchOrdersRequest();
        request.setLastOrderId(ORDER_ID);
        request.setUserId(USER_ID);
        request.setNum(10);
        request.setStatus(Collections.emptyList());

        FetchOrdersResponse response = new FetchOrdersResponse();
        response.setOrders(orders);
        mvc.perform(post("/fetchOrders")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(GSON.toJson(response)));

        verify(service).fetchOrders(anyInt(), anyList(), anyString(), anyInt());
    }

    @Test
    public void fetchOrdersWithException() throws Exception {
        ConsumerServerException e = FakeFactory.fakeException();
        when(service.fetchOrders(anyInt(), anyList(), anyString(), anyInt())).thenThrow(ConsumerServerException.class);
        FetchOrdersRequest request = new FetchOrdersRequest();
        request.setLastOrderId(ORDER_ID);
        request.setUserId(USER_ID);
        request.setNum(10);
        request.setStatus(Collections.emptyList());

        mvc.perform(post("/fetchOrders")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk());

        verify(service).fetchOrders(anyInt(), anyList(), anyString(), anyInt());
    }

    @Test
    public void fetchMenu() throws Exception {
        FetchMenuRequest request = new FetchMenuRequest();
        request.setUserId(USER_ID);

        List<Pizza> menus = FakeFactory.fakePizzas();
        Order cart = FakeFactory.fakeCartOrder();
        when(service.getInSaleMenu()).thenReturn(menus);
        when(service.getCartOrder(USER_ID, menus)).thenReturn(cart);

        FetchMenuResponse response = new FetchMenuResponse();
        response.setPizzas(menus);
        response.setCart(cart);
        mvc.perform(post("/fetchMenu")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(GSON.toJson(response)));

        verify(service).getInSaleMenu();
        verify(service).getCartOrder(USER_ID, menus);
    }

    @Test
    public void fetchMenuWithException() throws Exception {
        FetchMenuRequest request = new FetchMenuRequest();
        request.setUserId(USER_ID);

        List<Pizza> menus = FakeFactory.fakePizzas();
        when(service.getInSaleMenu()).thenReturn(menus);
        when(service.getCartOrder(USER_ID, menus)).thenThrow(ConsumerServerException.class);

        mvc.perform(post("/fetchMenu")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk());

        verify(service).getInSaleMenu();
        verify(service).getCartOrder(USER_ID, menus);
    }

    @Test
    public void updateOrder() throws Exception {
        when(service.updateOrder(ORDER_ID, PIZZA_ID, 2)).thenReturn(1);
        UpdateOrderRequest request = new UpdateOrderRequest();
        request.setOrderId(ORDER_ID);
        request.setCount(2);
        request.setPizzaId(PIZZA_ID);

        UpdateOrderResponse response = new UpdateOrderResponse();
        mvc.perform(post("/updateOrder")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(GSON.toJson(response)));

        verify(service).updateOrder(ORDER_ID, PIZZA_ID, 2);
    }

    @Test
    public void updateOrderFailure() throws Exception {
        when(service.updateOrder(ORDER_ID, PIZZA_ID, 2)).thenReturn(0);
        UpdateOrderRequest request = new UpdateOrderRequest();
        request.setOrderId(ORDER_ID);
        request.setCount(2);
        request.setPizzaId(PIZZA_ID);

        UpdateOrderResponse response = new UpdateOrderResponse();
        response.setResultType(ResultType.FAILURE);
        response.setErrorMsg("无法更改商品数量。");
        mvc.perform(post("/updateOrder")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(GSON.toJson(response)));

        verify(service).updateOrder(ORDER_ID, PIZZA_ID, 2);
    }

    @Test
    public void updateOrderWthException() throws Exception {
        when(service.updateOrder(ORDER_ID, PIZZA_ID, 2)).thenThrow(ConsumerServerException.class);
        UpdateOrderRequest request = new UpdateOrderRequest();
        request.setOrderId(ORDER_ID);
        request.setCount(2);
        request.setPizzaId(PIZZA_ID);

        mvc.perform(post("/updateOrder")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk());

        verify(service).updateOrder(ORDER_ID, PIZZA_ID, 2);
    }

    @Test
    public void sendOrder() throws Exception {
        Order order = FakeFactory.fakeOrder(OrderStatus.UNPAID);
        when(service.sendOrder(ORDER_ID, USER_ADDR_ID)).thenReturn(order);

        SendOrderRequest request = new SendOrderRequest();
        request.setOrderId(ORDER_ID);
        request.setUserAddressId(USER_ADDR_ID);

        SendOrderResponse response = new SendOrderResponse();
        response.setOrder(order);

        mvc.perform(post("/sendOrder")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(GSON.toJson(response)));

        verify(service).sendOrder(ORDER_ID, USER_ADDR_ID);
    }

    @Test
    public void sendOrderWithException() throws Exception {
        when(service.sendOrder(ORDER_ID, USER_ADDR_ID)).thenThrow(ConsumerServerException.class);

        SendOrderRequest request = new SendOrderRequest();
        request.setOrderId(ORDER_ID);
        request.setUserAddressId(USER_ADDR_ID);

        mvc.perform(post("/sendOrder")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk());

        verify(service).sendOrder(ORDER_ID, USER_ADDR_ID);
    }

    @Test
    public void cancelOrder() throws Exception {
        when(service.cancelOrder(ORDER_ID)).thenReturn(true);
        CancelOrderRequest request = new CancelOrderRequest();
        request.setOrderId(ORDER_ID);

        CancelOrderResponse response = new CancelOrderResponse();
        mvc.perform(post("/cancelOrder")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(GSON.toJson(response)));

        verify(service).cancelOrder(ORDER_ID);
    }

    @Test
    public void cancelOrderFailure() throws Exception {
        when(service.cancelOrder(ORDER_ID)).thenReturn(false);
        CancelOrderRequest request = new CancelOrderRequest();
        request.setOrderId(ORDER_ID);

        CancelOrderResponse response = new CancelOrderResponse();
        response.setResultType(ResultType.FAILURE);
        response.setErrorMsg("订单确认超过10分钟，无法取消。");
        mvc.perform(post("/cancelOrder")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(GSON.toJson(response)));

        verify(service).cancelOrder(ORDER_ID);
    }

    @Test
    public void cancelOrderWithException() throws Exception {
        when(service.cancelOrder(ORDER_ID)).thenThrow(ConsumerServerException.class);
        CancelOrderRequest request = new CancelOrderRequest();
        request.setOrderId(ORDER_ID);

        mvc.perform(post("/cancelOrder")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk());

        verify(service).cancelOrder(ORDER_ID);
    }

    @Test
    public void fetchPhone() throws Exception {
        OrderService.Phones phones = FakeFactory.fakePhones();
        when(service.getPhones(ORDER_ID)).thenReturn(phones);
        CancelOrderRequest request = new CancelOrderRequest();
        request.setOrderId(ORDER_ID);

        FetchPhoneResponse response = new FetchPhoneResponse();
        response.setShopPhone(phones.getShopPhone());
        response.setServicePhone(phones.getServicePhone());
        response.setDeliverymanPhone(phones.getDeliverymanPhone());
        mvc.perform(post("/fetchPhone")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(GSON.toJson(response)));

        verify(service).getPhones(ORDER_ID);
    }

    @Test
    public void fetchPhoneWithException() throws Exception {
        when(service.getPhones(ORDER_ID)).thenThrow(ConsumerServerException.class);
        CancelOrderRequest request = new CancelOrderRequest();
        request.setOrderId(ORDER_ID);

        mvc.perform(post("/fetchPhone")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk());

        verify(service).getPhones(ORDER_ID);
    }

    @Test
    public void pay() throws Exception {
        double price = 100.01;
        String returnForm = "<form>";
        when(service.payRequest(ORDER_ID, price)).thenReturn(returnForm);

        PayOrderRequest request  = new PayOrderRequest();
        request.setOrderId(ORDER_ID);
        request.setTotalPrice(price);
        PayOrderResponse response = new PayOrderResponse();
        response.setForm(returnForm);

        mvc.perform(post("/pay")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(GSON.toJson(response)));

        verify(service).payRequest(ORDER_ID, price);

    }

    @Test
    public void payWithException() throws Exception {
        double price = 100.01;
        when(service.payRequest(ORDER_ID, price)).thenThrow(ConsumerServerException.class);

        PayOrderRequest request = new PayOrderRequest();
        request.setOrderId(ORDER_ID);
        request.setTotalPrice(price);

        mvc.perform(post("/pay")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(GSON.toJson(request)))
                .andExpect(status().isOk());

        verify(service).payRequest(ORDER_ID, price);

    }
//        @Test
//    public void paid() throws Exception {
//        mvc.perform(post("/paid")
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .characterEncoding("UTF-8")
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//                .andExpect(view().name("success"));
//
//        verify(service).paid(any());
//    }
}