package edu.ecnu.scsse.pizza.bussiness.server.model.gaode;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.MapPoint;
import edu.ecnu.scsse.pizza.bussiness.server.model.entity.Point;

import java.util.ArrayList;
import java.util.List;


public class GaoDeMapUtil {

    private static String applicationKey = "b27829bd5eef90ff45a6831e1f4ffd1d";
    private static String bicycling_url = "https://restapi.amap.com/v4/direction/bicycling?";

    public BicyclingData driveRoutePlan(Point origin, Point destination) {
        BicyclingData bicyclingData = new BicyclingData();
        String params = "origin=" + origin.y() + "," + origin.x() + "&destination=" + destination.y() + "," + destination.x() + "&key=" + applicationKey;
        String url = bicycling_url + params;
        OkHttpUtil okHttpUtil=new OkHttpUtil();
        String result = okHttpUtil.get(url);
        JSONObject jsonObject= JSONObject.parseObject(result);
        bicyclingData.setErrcode(jsonObject.getIntValue("errcode"));
        bicyclingData.setErrdetail(jsonObject.getString("errdetail"));
        bicyclingData.setErrmsg(jsonObject.getString("errmsg"));

        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        bicyclingData.setOrigin(jsonObject1.getString("origin"));
        bicyclingData.setDestination(jsonObject1.getString("destination"));

        JSONArray jsonArray = jsonObject1.getJSONArray("paths");
        List<BicyclingPath> bicyclingPathList = new ArrayList<BicyclingPath>();
        if((jsonArray!= null) && (jsonArray.size() > 0)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                BicyclingPath bicyclingPath = new BicyclingPath();
                JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                bicyclingPath.setDistance(jsonObject2.getDoubleValue("distance"));
                bicyclingPath.setDuration(jsonObject2.getDoubleValue("duration"));
                JSONArray jsonArray1 = jsonObject1.getJSONArray("steps");
                List<BicyclingStep> bicyclingStepList = new ArrayList<BicyclingStep>();
                if ((jsonArray1 != null) && (jsonArray1.size() > 0)) {
                    for (int j = 0; j < jsonArray1.size(); j++) {
                        BicyclingStep bicyclingStep = new BicyclingStep();
                        JSONObject jsonObject3 = (JSONObject) jsonArray.get(i);
                        bicyclingStep.setDistance(jsonObject3.getDoubleValue("distance"));
                        bicyclingStep.setDuration(jsonObject3.getDoubleValue("duration"));
                        bicyclingStep.setInstruction(jsonObject3.getString("instruction"));
                        bicyclingStep.setRoad(jsonObject3.getString("road"));
                        bicyclingStep.setOrientation(jsonObject3.getString("orientation"));
                        bicyclingStep.setPolyline(jsonObject3.getString("polyline"));
                        bicyclingStep.setAction(jsonObject3.getString("action"));
                        bicyclingStep.setAssistantAction(jsonObject3.getString("assistant_action"));
                        bicyclingStepList.add(bicyclingStep);
                    }
                }
                bicyclingPath.setBicyclingStepList(bicyclingStepList);
                bicyclingPathList.add(bicyclingPath);
            }
        }
        bicyclingData.setBicyclingPathList(bicyclingPathList);
        return bicyclingData;
    }

    public static void main(String[] args){
        GaoDeMapUtil gaoDeMapUtil=new GaoDeMapUtil();
        Point x =new MapPoint(31.232495,121.412069);
        Point y =new MapPoint(31.238902,121.418865);
        System.out.println(gaoDeMapUtil.driveRoutePlan(x,y).total_duation());
    }
}