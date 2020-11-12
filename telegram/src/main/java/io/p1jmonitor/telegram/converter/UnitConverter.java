package io.p1jmonitor.telegram.converter;

import io.p1jmonitor.telegram.Unit;

public enum UnitConverter implements ValueConverter<Unit> {
    INSTANCE;

    @Override
    public Unit convert(String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }

        for (Unit unit : Unit.values()) {
            if (unit.getValue().equals(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
