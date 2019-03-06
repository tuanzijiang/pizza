package edu.ecnu.scsse.pizza.data.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AddressTag {
    COMMON(0, "常用");

    private int dbValue;

    private String expression;

    AddressTag(int dbValue, String expression) {
        this.dbValue = dbValue;
        this.expression = expression;
    }

    public int getDbValue() {
        return dbValue;
    }

    public String getExpression() {
        return expression;
    }

    public static AddressTag fromDbValue(Integer dbStatusValue) {
        if (dbStatusValue == null) {
            return null;
        }
        Optional<AddressTag> addressTag =  Arrays.stream(AddressTag.values())
                                    .filter(s -> s.dbValue == dbStatusValue)
                                    .findFirst();

        if (addressTag.isPresent()) {
            return addressTag.get();
        } else {
            return null;
        }
    }
}
