package edu.ecnu.scsse.pizza.bussiness.server.model.enums;

import java.util.Arrays;

public enum OperateObject {
    UNKNOWN(0, "未知"),
    MENU(1, "披萨"),
    INGREDIENT(2, "原料"),
    SHOP(3, "工厂"),
    DRIVER(4,"配送员");

    OperateObject(int dbValue, String expression) {
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

    public static OperateObject fromDbValue(Integer dbStatusValue) {
        if (dbStatusValue == null) {
            return UNKNOWN;
        }
        return Arrays.stream(OperateObject.values())
                .filter(s -> s.dbValue == dbStatusValue)
                .findFirst().orElse(UNKNOWN);
    }
}
