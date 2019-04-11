package edu.ecnu.scsse.pizza.data.bean;

import java.io.Serializable;

public class UserAddressBean implements Serializable {
    private Integer id;
    private String name;
    private Integer sex;
    private String address;
    private String addressDetail;
    private String phone;
//    private AddressTag tag;


    public UserAddressBean() {
    }

    public UserAddressBean(Integer id, String name, Integer sex, String address, String addressDetail, String phone) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.addressDetail = addressDetail;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
//
//    public AddressTag getTag() {
//        return tag;
//    }
//
//    public void setTag(AddressTag tag) {
//        this.tag = tag;
//    }
}
