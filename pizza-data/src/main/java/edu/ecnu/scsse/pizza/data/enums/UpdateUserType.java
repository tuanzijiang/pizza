package edu.ecnu.scsse.pizza.data.enums;

public enum UpdateUserType {
    NAME(0),
    PHONE(1),
    EMAIL(2),
    BIRTHDAY(3),
    CITY(4),
    IMGAE(5),
    ADDRESSID(6);

    int value;

    UpdateUserType(int value) {
        this.value=value;
    }
}
