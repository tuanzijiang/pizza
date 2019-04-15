package edu.ecnu.scsse.pizza.consumer.server.utils;

import com.google.gson.Gson;
import edu.ecnu.scsse.pizza.consumer.server.FakeFactory;
import edu.ecnu.scsse.pizza.consumer.server.exception.ThirdPartyException;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static edu.ecnu.scsse.pizza.consumer.server.utils.ThrowableCaptor.thrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpClients.class})
@PowerMockIgnore("javax.management.*")
public class HttpUtilsTest {

    private static final Gson GSON = new Gson();

    @Test
    public void commitOrder() throws IOException, ThirdPartyException {
        PowerMockito.mockStatic(HttpClients.class);
        CloseableHttpClient client = PowerMockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse response = PowerMockito.mock(CloseableHttpResponse.class);
        HttpEntity entity = mock(HttpEntity.class);

        OrderEntity orderEntity = FakeFactory.fakeOrderEntity(1, "AAA",
                1, 1, OrderStatus.WAIT_DELIVERY).get();
        OrderReceiveResponse orderReceiveResponse = new OrderReceiveResponse();
        orderReceiveResponse.setResultType(ResultType.SUCCESS);
        orderReceiveResponse.setOrderEntity(orderEntity);

        StringEntity stringEntity = new StringEntity(GSON.toJson(orderReceiveResponse), StandardCharsets.UTF_8);

        when(entity.getContent()).thenReturn(stringEntity.getContent());
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(any())).thenReturn(response);
        PowerMockito.when(HttpClients.createDefault()).thenReturn(client);
        OrderEntity result = HttpUtils.commitOrder("AAA", 8);
        Assert.assertNotNull(result);
    }

    @Test
    public void commitOrderWithNullResponse() throws IOException {

        PowerMockito.mockStatic(HttpClients.class);
        CloseableHttpClient client = PowerMockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse response = PowerMockito.mock(CloseableHttpResponse.class);

        when(response.getEntity()).thenReturn(null);
        when(client.execute(any())).thenReturn(response);
        PowerMockito.when(HttpClients.createDefault()).thenReturn(client);
        ThirdPartyException result = (ThirdPartyException) thrownBy(() ->
                HttpUtils.commitOrder("AAA", 8));
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getMessage(), "The response is NULL.");
    }

    @Test
    public void commitOrderWithNullEntity() throws IOException {

        PowerMockito.mockStatic(HttpClients.class);
        CloseableHttpClient client = PowerMockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse response = PowerMockito.mock(CloseableHttpResponse.class);
        HttpEntity entity = mock(HttpEntity.class);

        OrderReceiveResponse orderReceiveResponse = new OrderReceiveResponse();
        orderReceiveResponse.setResultType(ResultType.SUCCESS);
        orderReceiveResponse.setOrderEntity(null);

        StringEntity stringEntity = new StringEntity(GSON.toJson(orderReceiveResponse), StandardCharsets.UTF_8);

        when(entity.getContent()).thenReturn(stringEntity.getContent());
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(any())).thenReturn(response);
        PowerMockito.when(HttpClients.createDefault()).thenReturn(client);

        ThirdPartyException result = (ThirdPartyException) thrownBy(() ->
                HttpUtils.commitOrder("AAA", 8));
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getMessage(),
                String.format("The response is not available. response=%s", GSON.toJson(orderReceiveResponse)));
    }

    @Test
    public void commitOrderWithFailureResponse() throws IOException {

        PowerMockito.mockStatic(HttpClients.class);
        CloseableHttpClient client = PowerMockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse response = PowerMockito.mock(CloseableHttpResponse.class);
        HttpEntity entity = mock(HttpEntity.class);

        OrderReceiveResponse orderReceiveResponse = new OrderReceiveResponse();
        orderReceiveResponse.setResultType(ResultType.FAILURE);
        orderReceiveResponse.setOrderEntity(null);

        StringEntity stringEntity = new StringEntity(GSON.toJson(orderReceiveResponse), StandardCharsets.UTF_8);

        when(entity.getContent()).thenReturn(stringEntity.getContent());
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(any())).thenReturn(response);
        PowerMockito.when(HttpClients.createDefault()).thenReturn(client);

        ThirdPartyException result = (ThirdPartyException) thrownBy(() ->
                HttpUtils.commitOrder("AAA", 8));
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getMessage(),
                String.format("The resultType is not SUCCESS. response=%s", GSON.toJson(orderReceiveResponse)));
    }

}