package edu.ecnu.scsse.pizza.bussiness.server;

import edu.ecnu.scsse.pizza.bussiness.server.model.enums.OperateType;
import edu.ecnu.scsse.pizza.data.domain.OperateLoggerEntity;
import edu.ecnu.scsse.pizza.data.domain.UserAddressEntity;
import edu.ecnu.scsse.pizza.data.domain.UserEntity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FakeAdmin {
    public static OperateLoggerEntity fakeOperateLogger(int adminId,String type, String obj, String result){
        OperateLoggerEntity entity = new OperateLoggerEntity();
        entity.setId(1);
        entity.setAdminId(adminId);
        entity.setOperateType(type);
        entity.setOperateDetail(type+obj+result);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = df.format(date);
        Timestamp ts = Timestamp.valueOf(dateStr);
        entity.setOperateTime(new Timestamp(ts.getTime()));
        return entity;
    }

    public static List<OperateLoggerEntity> fakeLoggers(){
        List<OperateLoggerEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OperateLoggerEntity entity = new OperateLoggerEntity();
            entity.setId(i);
            entity.setAdminId(i);
            entity.setOperateType(OperateType.UPDATE.getExpression());
            entity.setOperateDetail("operateDetail");
            entity.setOperateTime(new Timestamp((new Date()).getTime()));
            list.add(entity);
        }
        return list;
    }

    public static List<UserEntity> fakeUserList(){
        List<UserEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserEntity entity = new UserEntity();
            entity.setId(i);
            entity.setName("user"+i);
            entity.setCity("Shanghai");
            entity.setEmail("email");
            entity.setBirthday(new java.sql.Date((new Date()).getTime()));
            entity.setLatestLoginTime(new Timestamp((new Date()).getTime()));
            entity.setDefaultUserAddressId(1);
            list.add(entity);
        }
        return list;
    }

    public static UserEntity fakeUser(int userId){
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setName("user"+userId);
        entity.setCity("Shanghai");
        entity.setEmail("email");
        entity.setBirthday(new java.sql.Date((new Date()).getTime()));
        entity.setLatestLoginTime(new Timestamp((new Date()).getTime()));
        entity.setDefaultUserAddressId(1);
        return entity;
    }

    public static UserAddressEntity fakeUserAddress(int userId, int addressId){
        UserAddressEntity entity = new UserAddressEntity();
        entity.setId(1);
        entity.setUserId(userId);
        entity.setAddressId(addressId);
        entity.setAddressDetail("addressDetail");
        entity.setName("name");
        entity.setPhone("111");
        return entity;
    }
}
