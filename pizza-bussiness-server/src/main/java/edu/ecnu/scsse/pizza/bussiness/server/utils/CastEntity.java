package edu.ecnu.scsse.pizza.bussiness.server.utils;

import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CastEntity {
    private static Class[] orderBeanClass = new Class[]{Integer.class,Integer.class,Integer.class,
            Double.class,Integer.class,Integer.class,Timestamp.class,Timestamp.class,Timestamp.class,
            String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class};

    private static Class[]  menuBeanClass = new Class[]{Integer.class,String.class,String.class,
            String.class,Double.class,Integer.class,Integer.class,Integer.class};

    private static Class[] ingredientBeanClass = new Class[]{Integer.class,String.class,String.class,
            Integer.class,Integer.class,Integer.class,Integer.class};

    private static Class[] driverBeanClass = new Class[]{Integer.class,String.class,String.class,Integer.class,Integer.class,String.class};

    //转换OrderBean
    public static <T> List<T> castEntityToOrderBean(List<Object[]> list, Class<T> clazz) throws Exception {
        List<T> returnList = new ArrayList<T>();
        Class[] c2 = orderBeanClass;

        for (Object[] o : list) {
            Constructor<T> constructor = clazz.getConstructor(c2);
            returnList.add(constructor.newInstance(o));
        }

        return returnList;
    }

    //转换MenuBean
    public static <T> List<T> castEntityToMenuBean(List<Object[]> list, Class<T> clazz) throws Exception {
        List<T> returnList = new ArrayList<T>();
        Class[] c2 = menuBeanClass;

        for (Object[] o : list) {
            Constructor<T> constructor = clazz.getConstructor(c2);
            returnList.add(constructor.newInstance(o));
        }

        return returnList;
    }

    //转换IngredientBean
    public static <T> List<T> castEntityToIngredientBean(List<Object[]> list, Class<T> clazz) throws Exception {
        List<T> returnList = new ArrayList<T>();
        Class[] c2 = ingredientBeanClass;

        for (Object[] o : list) {
            Constructor<T> constructor = clazz.getConstructor(c2);
            returnList.add(constructor.newInstance(o));
        }

        return returnList;
    }

    //转换DriverBean
    public static <T> List<T> castEntityToDriverBean(List<Object[]> list, Class<T> clazz) throws Exception {
        List<T> returnList = new ArrayList<T>();
        Class[] c2 = driverBeanClass;

        for (Object[] o : list) {
            Constructor<T> constructor = clazz.getConstructor(c2);
            returnList.add(constructor.newInstance(o));
        }

        return returnList;
    }

    //转换实体类
    public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz) throws Exception {
        List<T> returnList = new ArrayList<T>();
        Object[] co = list.get(0);
        Class[] c2 = new Class[co.length];

        //确定构造方法
        for (int i = 0; i < co.length; i++) {
            c2[i] = co[i].getClass();
        }

        for (Object[] o : list) {
            Constructor<T> constructor = clazz.getConstructor(c2);
            returnList.add(constructor.newInstance(o));
        }

        return returnList;
    }
}
