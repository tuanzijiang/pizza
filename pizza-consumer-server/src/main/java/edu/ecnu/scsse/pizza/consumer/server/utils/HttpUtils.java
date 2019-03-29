package edu.ecnu.scsse.pizza.consumer.server.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import edu.ecnu.scsse.pizza.data.domain.OrderEntity;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private static final String AMAP_URL = "https://restapi.amap.com/v3/geocode/geo?key=be738875154909f7a2408f3f96e7871a&address=%s";

    private static final String BUSSINESS_SERVICE_URL = "http://139.224.238.171:8088/pizza-bussiness/orderReceive/getReceiveShopId";

    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
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
    public static OrderEntity commitOrder(String orderUuid, Integer userAddressId) throws IOException {
        Map<String, String> param = new HashMap<>();
        param.put("orderUuid", orderUuid);
        param.put("userAddressId", String.valueOf(userAddressId));
        return post(BUSSINESS_SERVICE_URL, param, OrderEntity.class);
    }

    private static <T> T post(String url, Map<String, String> params, Class<T> targetClass) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
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
            JsonReader reader = new JsonReader(new InputStreamReader(httpEntity.getContent(),
                    StandardCharsets.UTF_8));
            result = GSON.fromJson(reader, targetClass);
        }

        try {
            httpclient.close();
        } catch (IOException e) {
            log.error("Fail to close the http client.", e);
        }
        return result;
    }
}