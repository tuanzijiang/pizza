package edu.ecnu.scsse.pizza.consumer.server.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int id;
    private String name;
    private String phone;
    private String email;
    private Date birthday;
    private String city;
    private String img;
    private Address address;

    public User(int id, String name, String phone, String email, Date birthday, String city, String img) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.city = city;
        this.img = img;
    }
}

