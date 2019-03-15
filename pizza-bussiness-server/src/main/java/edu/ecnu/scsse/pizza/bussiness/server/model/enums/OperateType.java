package edu.ecnu.scsse.pizza.bussiness.server.model.enums;

import java.util.Arrays;

public enum OperateType {
    UNKNOWN(0, "未知"),
    INSERT(1, "增加"),
    UPDATE(2, "修改"),
    DELETE(3, "删除");

    OperateType(int dbValue, String expression) {
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

    public static OperateType fromDbValue(Integer dbStatusValue) {
        if (dbStatusValue == null) {
            return UNKNOWN;
        }
        return Arrays.stream(OperateType.values())
                .filter(s -> s.dbValue == dbStatusValue)
                .findFirst().orElse(UNKNOWN);
    }
}
