package edu.ecnu.scsse.pizza.data.enums;

import java.util.Arrays;
import java.util.Optional;

public enum  PizzaStatus {
    IN_SALE(0, "售卖中"),
    OFF_SHELF(1, "已下架");

    private int dbValue;

    private String expression;

    PizzaStatus(int dbValue, String expression) {
        this.dbValue = dbValue;
        this.expression = expression;
    }

    public int getDbValue() {
        return dbValue;
    }

    public String getExpression() {
        return expression;
    }

    public static PizzaStatus fromDbValue(Integer dbStatusValue) {
        if (dbStatusValue == null) {
            return null;
        }
        Optional<PizzaStatus> pizzaStatus =  Arrays.stream(PizzaStatus.values())
                .filter(s -> s.dbValue == dbStatusValue)
                .findFirst();

        if (pizzaStatus.isPresent()) {
            return pizzaStatus.get();
        } else {
            return null;
        }
    }
}
