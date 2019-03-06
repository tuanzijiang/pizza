package edu.ecnu.scsse.pizza.bussiness.server.model.entity;

import edu.ecnu.scsse.pizza.data.domain.UserEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class User {
    private String id;
    private String image;
    private String name;
    private String email;
    private String phone;
    private String weChat;
    private String defaultUserAddress;
    private String city;
    private String birthday;
    private String latestLoginTime;

    public User() {
    }
    public User(UserEntity userEntity){
        this.id = String.valueOf(userEntity.getId());
        this.image = userEntity.getImage();
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.phone = userEntity.getPhone();
        this.weChat = userEntity.getWechat();
        this.city = userEntity.getCity();

        String birthdayPattern = "yyyy/MM/dd";
        DateFormat df = new SimpleDateFormat(birthdayPattern);
        this.birthday = df.format(userEntity.getBirthday());

        String loginTimePattern = "yyyy/MM/dd hh/MM/ss";
        DateFormat df2 = new SimpleDateFormat(loginTimePattern);
        this.latestLoginTime = df.format(userEntity.getLatestLoginTime());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getDefaultUserAddress() {
        return defaultUserAddress;
    }

    public void setDefaultUserAddress(String defaultUserAddress) {
        this.defaultUserAddress = defaultUserAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLatestLoginTime() {
        return latestLoginTime;
    }

    public void setLatestLoginTime(String latestLoginTime) {
        this.latestLoginTime = latestLoginTime;
    }
}
