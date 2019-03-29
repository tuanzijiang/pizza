package edu.ecnu.scsse.pizza.consumer.server.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.google.gson.Gson;
import edu.ecnu.scsse.pizza.consumer.server.FakeFactory;
import edu.ecnu.scsse.pizza.consumer.server.exception.ConsumerServerException;
import edu.ecnu.scsse.pizza.consumer.server.exception.IllegalArgumentException;
import edu.ecnu.scsse.pizza.consumer.server.exception.PayFailureException;
import edu.ecnu.scsse.pizza.consumer.server.exception.ServiceException;
import edu.ecnu.scsse.pizza.consumer.server.model.entity.Order;
import edu.ecnu.scsse.pizza.consumer.server.utils.HttpUtils;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static edu.ecnu.scsse.pizza.consumer.server.utils.ThrowableCaptor.thrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OrderService.class, HttpUtils.class})
@PowerMockIgnore("javax.management.*")
public class OrderServiceStaticTest {

    private static final Gson GSON = new Gson();

    private OrderService orderService;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(OrderService.class);
        orderService = new OrderService();
    }

    @Test
    public void payRequestWithNullOrderId() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() -> orderService.payRequest(null, 20.12));
        assertEquals(e.getMessage(), "orderUuid can't be null.");
    }

    @Test
    public void payRequestWithZeroPrice() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() -> orderService.payRequest("AAA", 0));
        assertEquals(e.getMessage(), "totalPrice must be positive.");
    }

    @Test
    public void payRequestWithNegativePrice() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() -> orderService.payRequest("AAA", -10));
        assertEquals(e.getMessage(), "totalPrice must be positive.");
    }

    @Test
    public void payRequest() throws Exception {
        String returnString = "body";
        DefaultAlipayClient client = PowerMockito.mock(DefaultAlipayClient.class);
        AlipayTradeWapPayResponse payResponse = new AlipayTradeWapPayResponse();
        payResponse.setBody(returnString);
        Mockito.when(client.pageExecute(any())).thenReturn(payResponse);
        PowerMockito.whenNew(DefaultAlipayClient.class).withAnyArguments().thenReturn(client);

        double price = 20.12;
        String orderUuid = "AAA";

        String result = orderService.payRequest(orderUuid, price);
        assertEquals(returnString, result);
    }

    @Test
    public void payRequestFailure() throws Exception {
        String errorMsg = "Error Msg.";
        DefaultAlipayClient client = PowerMockito.mock(DefaultAlipayClient.class);
        AlipayTradeWapPayResponse payResponse = new AlipayTradeWapPayResponse();
        payResponse.setSubCode("1122");
        payResponse.setMsg(errorMsg);
        Mockito.when(client.pageExecute(any())).thenReturn(payResponse);
        PowerMockito.whenNew(DefaultAlipayClient.class).withAnyArguments().thenReturn(client);

        double price = 20.12;
        String orderUuid = "AAA";

        PayFailureException e = (PayFailureException) thrownBy(() -> orderService.payRequest(orderUuid, price));
        assertEquals(e.getMessage(), payResponse.getMsg());
    }

    @Test
    public void payRequestWithAlipayApiException() throws Exception {
        DefaultAlipayClient client = PowerMockito.mock(DefaultAlipayClient.class);
        AlipayApiException exception = new AlipayApiException();
        Mockito.when(client.pageExecute(any())).thenThrow(exception);
        PowerMockito.whenNew(DefaultAlipayClient.class).withAnyArguments().thenReturn(client);

        double price = 20.12;
        String orderUuid = "AAA";

        PayFailureException e = (PayFailureException) thrownBy(() -> orderService.payRequest(orderUuid, price));
        assertEquals(e.getCause(), exception);
    }

    @Test
    public void sendOrderWithNullOrderUuid() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.sendOrder(null, 1));
        assertEquals(e.getMessage(), "orderUuid can't be null.");
    }

    @Test
    public void sendOrderWithZeroUserAddressId() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.sendOrder("AAA", 0));
        assertEquals(e.getMessage(), "userAddressId must be positive.");
    }

    @Test
    public void sendOrderWithNegativeUserAddressId() {
        IllegalArgumentException e = (IllegalArgumentException) thrownBy(() ->
                orderService.sendOrder("AAA", -1));
        assertEquals(e.getMessage(), "userAddressId must be positive.");
    }

    @Test
    public void sendOrder() throws IOException, ConsumerServerException {
        String orderUuid = "AAA";
        Integer userAddressId = 1;
        PowerMockito.mockStatic(HttpUtils.class);
        OrderEntity entity = FakeFactory.fakeOrderEntity(1, orderUuid, 1, 1, OrderStatus.WAIT_DELIVERY).get();
        PowerMockito.when(HttpUtils.commitOrder(orderUuid, userAddressId)).thenReturn(entity);
        Order order = orderService.sendOrder(orderUuid, userAddressId);
        assertEquals(order.getStatus(), OrderStatus.WAIT_DELIVERY);
        assertEquals(order.getId(), orderUuid);
    }

    @Test
    public void sendOrderWithIOException() throws IOException, ConsumerServerException {
        String orderUuid = "AAA";
        Integer userAddressId = 1;
        PowerMockito.mockStatic(HttpUtils.class);
        PowerMockito.when(HttpUtils.commitOrder(orderUuid, userAddressId)).thenThrow(IOException.class);
        ServiceException exception = (ServiceException) thrownBy(() -> orderService.sendOrder(orderUuid, userAddressId));
        assertEquals(exception.getMessage(), "I/O Exception while sending http request to Business Server.");
    }
}
