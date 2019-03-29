package edu.ecnu.scsse.pizza.consumer.server.utils;

import com.google.gson.Gson;
import edu.ecnu.scsse.pizza.consumer.server.FakeFactory;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import edu.ecnu.scsse.pizza.data.enums.OrderStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpClients.class})
@PowerMockIgnore("javax.management.*")
public class HttpUtilsTest {

    private String FORMAT_ADDR = "上海市普陀区华东师范大学(中北校区)";
    private String ADDR1 = "上海市普陀区中山北路3663号";
    private String ADDR2 = "华东师范大学(中北校区)";


    @Test
    public void queryGeo() throws IOException {
        AmapLocation.Geocode code1 = HttpUtils.queryGeo(ADDR1);
        AmapLocation.Geocode code2 = HttpUtils.queryGeo(ADDR2);
        Assert.assertEquals(code1.getFormattedAddress(), FORMAT_ADDR);
        Assert.assertEquals(code2.getFormattedAddress(), FORMAT_ADDR);
    }

    @Test
    public void commitOrder() throws IOException {

        Gson gson = new Gson();

        PowerMockito.mockStatic(HttpClients.class);
        CloseableHttpClient client = PowerMockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse response = PowerMockito.mock(CloseableHttpResponse.class);
        HttpEntity entity = mock(HttpEntity.class);

        OrderEntity orderEntity = FakeFactory.fakeOrderEntity(1, "AAA",
                1, 1, OrderStatus.WAIT_DELIVERY).get();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(gson.toJson(orderEntity).getBytes(Charset.forName("utf-8")));
        InputStream is = new ByteArrayInputStream(baos.toByteArray());

        when(entity.getContent()).thenReturn(is);
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(any())).thenReturn(response);
        PowerMockito.when(HttpClients.createDefault()).thenReturn(client);
        OrderEntity result = HttpUtils.commitOrder("AAA", 8);
        Assert.assertNotNull(result);
    }
}