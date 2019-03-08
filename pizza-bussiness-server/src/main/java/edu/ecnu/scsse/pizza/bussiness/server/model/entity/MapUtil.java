package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import java.math.BigDecimal;

public class MapUtil {

    private static final double EARTH_RADIUS = 637_1393.00D;
    private static final double RADIAN = Math.PI / 180.00D;
    private static final double HALF = 0.5D;

    /**
     * 比如
     * point1 30，120
     * point2 31，121
     * 两点之间距离为146784.74米，约146.78公里
     *
     * @param point1 坐标点1
     * @param point2 坐标点2
     * @return 返回double 类型的距离，向上进行四舍五入，距离精确到厘米，单位为米
     */
    public static double distanceOf(Point point1, Point point2) {
        double lat1 = point1.x();
        double lon1 = point1.y();
        double lat2 = point2.x();
        double lon2 = point2.y();
        double x, y, a, b, distance;

        lat1 *= RADIAN;
        lat2 *= RADIAN;
        x = lat1 - lat2;
        y = lon1 - lon2;
        y *= RADIAN;
        a = Math.sin(x * HALF);
        b = Math.sin(y * HALF);
        distance = EARTH_RADIUS * Math.asin(Math.sqrt(a * a + Math.cos(lat1) * Math.cos(lat2) * b * b)) / HALF;

        return new BigDecimal(distance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
