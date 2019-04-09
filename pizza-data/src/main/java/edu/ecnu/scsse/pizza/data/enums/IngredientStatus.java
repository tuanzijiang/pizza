package edu.ecnu.scsse.pizza.data.enums;

import java.util.Arrays;
import java.util.Optional;

public enum IngredientStatus {
    USING(0,"使用中"),
    TERMINATED(1,"已停用");

    private int dbValue;
    private String expression;

    IngredientStatus(int dbValue, String expression) {
        this.dbValue = dbValue;
        this.expression = expression;
    }

    public int getDbValue() {
        return dbValue;
    }

    public String getExpression() {
        return expression;
    }

    public static IngredientStatus fromDbValue(Integer dbStatusValue) {
        if (dbStatusValue == null) {
            return null;
        }
        Optional<IngredientStatus> ingredientStatus =  Arrays.stream(IngredientStatus.values())
                .filter(s -> s.dbValue == dbStatusValue)
                .findFirst();

        if (ingredientStatus.isPresent()) {
            return ingredientStatus.get();
        } else {
            return null;
        }
    }

    public static IngredientStatus fromExpression(String expression) {
        if (expression == null) {
            return null;
        }
        Optional<IngredientStatus> ingredientStatus =  Arrays.stream(IngredientStatus.values())
                .filter(s -> s.expression.equals(expression))
                .findFirst();

        if (ingredientStatus.isPresent()) {
            return ingredientStatus.get();
        } else {
            return null;
        }
    }
}
