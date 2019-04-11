package edu.ecnu.scsse.pizza.consumer.server.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import edu.ecnu.scsse.pizza.consumer.server.exception.ThirdPartyException;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private static final String AMAP_URL = "https://restapi.amap.com/v3/geocode/geo?key=be738875154909f7a2408f3f96e7871a&address=%s";

    private static final String BUSINESS_SERVICE_URL = "http://139.224.238.171:8088/pizza-business/orderReceive/getReceiveShopId";

    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();


    /**
     * 查询地址经纬度
     *
     * @param address 规范化地址
     * @return 高德GEO数据
     * @throws IOException
     */
    public static AmapLocation.Geocode queryGeo(String address) throws IOException {
        AmapLocation location = get(String.format(AMAP_URL, address), AmapLocation.class);
        if (location != null && !CollectionUtils.isEmpty(location.getGeocodes())) {
            return location.getGeocodes().get(0);
        }
        return null;
    }

    /**
     * 调用B端订单提交服务
     *
     * @param orderUuid
     * @param userAddressId
     * @return
     * @throws IOException
     */
    public static OrderEntity commitOrder(String orderUuid, Integer userAddressId) throws IOException, ThirdPartyException {
        OrderReceiveRequest receiveRequest = new OrderReceiveRequest();
        receiveRequest.setOrderUuid(orderUuid);
        receiveRequest.setUserAddressId(userAddressId);
        JsonObject jsonObject = GSON.toJsonTree(receiveRequest).getAsJsonObject();
        OrderReceiveResponse response = post(BUSINESS_SERVICE_URL, jsonObject, OrderReceiveResponse.class);
        if (response == null) {
            throw new ThirdPartyException("第三方服务忙，请稍后重试。", "The response is NULL.");
        }
        if (!Objects.equals(response.getResultType(), ResultType.SUCCESS)) {
            throw new ThirdPartyException("第三方服务忙，请稍后重试。",
                    String.format("The resultType is not SUCCESS. response=%s", GSON.toJson(response)),
                    response.getCause());
        }
        if (response.getOrderEntity() == null) {
            throw new ThirdPartyException("第三方服务响应异常。",
                    String.format("The response is not available. response=%s", GSON.toJson(response)));
        }

        return response.getOrderEntity();
    }

    private static <T> T post(String url, JsonObject params, Class<T> targetClass) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setEntity(new StringEntity(params.toString(), Charset.forName("UTF-8")));
        return invoke(httpclient, httpPost, targetClass);
    }

    private static <T> T get(String url, Class<T> targetClass) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        return invoke(httpclient, httpGet, targetClass);
    }

    private static <T> T invoke(CloseableHttpClient httpclient, HttpUriRequest request, Class<T> targetClass) throws IOException {
        T result = null;
        CloseableHttpResponse response  = httpclient.execute(request);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            JsonReader reader = new JsonReader(new InputStreamReader(httpEntity.getContent(), StandardCharsets.UTF_8));
            result = GSON.fromJson(reader, targetClass);
        }

        try {
            httpclient.close();
        } catch (IOException e) {
            log.error("Fail to close the http client.", e);
        }
        return result;
    }
//
//    public static void main(String[] args) throws IOException, ThirdPartyException {
//
//        OrderEntity entity = HttpUtils.commitOrder("AAB", 8);
//        System.out.println(entity);
//    }
}