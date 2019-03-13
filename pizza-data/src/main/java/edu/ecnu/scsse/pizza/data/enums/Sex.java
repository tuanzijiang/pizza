package edu.ecnu.scsse.pizza.data.enums;

import java.util.Arrays;

public enum Sex {
    MALE(0),
    FEMALE(1);

    Sex(int dbValue) {
        this.dbValue = dbValue;
    }

    private int dbValue;

    public int getDbValue() {
        return this.dbValue;
    }

    public static Sex fromDbValue(Integer dbStatusValue) {
        if (dbStatusValue == null) {
            return null;
        }
        return Arrays.stream(Sex.values())
                .filter(s -> s.dbValue == dbStatusValue)
                .findFirst().orElse(null);
    }
}
