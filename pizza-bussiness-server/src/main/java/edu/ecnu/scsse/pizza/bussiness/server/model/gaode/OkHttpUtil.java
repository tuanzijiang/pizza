package edu.ecnu.scsse.pizza.bussiness.server.model.gaode;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {
    public String get(String url){
        String result = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            result = response.body().string();
            return result;
        } catch (Exception e) {
            return result;
        }
    }
}
