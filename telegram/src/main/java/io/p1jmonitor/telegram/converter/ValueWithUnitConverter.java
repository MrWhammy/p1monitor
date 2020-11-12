package io.p1jmonitor.telegram.converter;

import io.p1jmonitor.telegram.ValueWithUnit;

public class ValueWithUnitConverter<T extends Number> implements ValueConverter<ValueWithUnit<T>> {
    private final ValueConverter<T> valueConverter;

    public ValueWithUnitConverter(ValueConverter<T> valueConverter) {
        this.valueConverter = valueConverter;
    }

    @Override
    public ValueWithUnit<T> convert(String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }

        int starIndex = value.indexOf('*');
        if (starIndex < 1 || starIndex >= value.length() - 1) {
            throw new IllegalArgumentException(value);
        }

        return new ValueWithUnit<>(valueConverter.convert(value.substring(0, starIndex)), UnitConverter.INSTANCE.convert(value.substring(starIndex + 1)));
    }
}
