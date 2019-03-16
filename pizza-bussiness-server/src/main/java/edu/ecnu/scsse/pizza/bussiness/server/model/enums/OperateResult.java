package edu.ecnu.scsse.pizza.bussiness.server.model.enums;

import java.util.Arrays;

public enum OperateResult {
    SUCCESS(0, "成功"),
    FAILURE(1, "失败");

    OperateResult(int dbValue, String expression) {
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

    public static OperateResult fromDbValue(Integer dbStatusValue) {
        if (dbStatusValue == null) {
            return FAILURE;
        }
        return Arrays.stream(OperateResult.values())
                .filter(s -> s.dbValue == dbStatusValue)
                .findFirst().orElse(FAILURE);
    }
}
