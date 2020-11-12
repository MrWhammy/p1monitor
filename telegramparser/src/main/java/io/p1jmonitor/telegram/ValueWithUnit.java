package io.p1jmonitor.telegram;

public class ValueWithUnit<T extends Number> {
    private final T value;
    private final Unit unit;

    public ValueWithUnit(T value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public T getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }
}
