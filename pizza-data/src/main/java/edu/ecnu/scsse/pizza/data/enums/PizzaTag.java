package edu.ecnu.scsse.pizza.data.enums;

import java.util.Arrays;
import java.util.Optional;

public enum PizzaTag {

    UNKNOWN(-1, "披萨")
    // todo - add pizza tags !
    ;

    private int dbValue;

    private String expression;

    PizzaTag(int dbValue, String expression) {
        this.dbValue = dbValue;
        this.expression = expression;
    }

    public int getDbValue() {
        return dbValue;
    }

    public String getExpression() {
        return expression;
    }

    public static PizzaTag fromDbValue(Integer dbStatusValue) {
        if (dbStatusValue != null) {
            Optional<PizzaTag> pizzaTag =  Arrays.stream(PizzaTag.values())
                    .filter(s -> s.dbValue == dbStatusValue)
                    .findFirst();

            if (pizzaTag.isPresent()) {
                return pizzaTag.get();
            }
        }

        return UNKNOWN;
    }
}
