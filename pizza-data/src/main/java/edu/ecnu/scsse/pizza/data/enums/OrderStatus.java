package edu.ecnu.scsse.pizza.data.enums;

import java.util.Arrays;

public enum OrderStatus {
    UNKNOWN(0, "未知"),
    CART(1, "购物车"),
    UNPAID(2, "待支付"),
    PAID(3, "已支付"),
    CANCEL_CHECKING(4, "取消审核中"),
    CANCELED(5, "已取消"),
    CANCEL_FAILED(6, "取消失败"),
    DELIVERING(7, "配送中"),
    RECEIVED(8, "已送达"),
    FINISH(9, "已完成"),
    WAIT_DELIVERY(10,"待配送"),
    RECEIVE_FAIL(11,"商家接单失败");

    OrderStatus(int dbValue, String expression) {
        this.dbValue = dbValue;
        this.expression = expression;
    }

    private int dbValue;

    private String expression;

    public int getDbValue() {
        return dbValue;
    }

    public String getExpression() {
        return expression;
    }

    public static OrderStatus fromDbValue(Integer dbStatusValue) {
        if (dbStatusValue == null) {
            return UNKNOWN;
        }
        return Arrays.stream(OrderStatus.values())
                .filter(s -> s.dbValue == dbStatusValue)
                .findFirst().orElse(UNKNOWN);
    }
}
