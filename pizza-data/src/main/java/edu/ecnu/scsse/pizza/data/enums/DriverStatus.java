package edu.ecnu.scsse.pizza.data.enums;

import java.util.Arrays;
import java.util.Optional;

public enum DriverStatus {
    LEISURE(0,"空闲"),
    BUSY(1,"忙碌");

    private int dbValue;
    private String expression;

    DriverStatus(int dbValue, String expression) {
        this.dbValue = dbValue;
        this.expression = expression;
    }

    public int getDbValue() {
        return dbValue;
    }

    public String getExpression() {
        return expression;
    }

    public static DriverStatus fromDbValue(Integer dbStatusValue) {
        if (dbStatusValue == null) {
            return null;
        }
        Optional<DriverStatus> driverStatus =  Arrays.stream(DriverStatus.values())
                .filter(s -> s.dbValue == dbStatusValue)
                .findFirst();

        if (driverStatus.isPresent()) {
            return driverStatus.get();
        } else {
            return null;
        }
    }
}
