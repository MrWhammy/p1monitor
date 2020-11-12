package io.p1jmonitor.telegram.converter;

import io.p1jmonitor.telegram.ObisReference;
import io.p1jmonitor.telegram.Unit;
import io.p1jmonitor.telegram.ValueWithUnit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class ValueConverterFactory {

    private final ZonedDateTimeConverter zonedDateTimeConverter;

    public ValueConverterFactory(ZonedDateTimeConverter zonedDateTimeConverter) {
        this.zonedDateTimeConverter = zonedDateTimeConverter;
    }

    public ValueConverter<?> forObisCodeAndType(ObisReference obisReference, Class<?> type) {
        if (type == String.class) {
            return StringValueConverter.INSTANCE;
        } else if (type == LocalDateTime.class) {
            return zonedDateTimeConverter;
        } else if (type == BigDecimal.class) {
            return BigDecimalConverter.INSTANCE;
        } else if (type == BigInteger.class) {
            return BigIntegerConverter.INSTANCE;
        } else if (type == Unit.class) {
            return UnitConverter.INSTANCE;
        } else if (type == ValueWithUnit.class) {
            if (obisReference.maximumDecimals() > 0) {
                return new ValueWithUnitConverter<>(BigDecimalConverter.INSTANCE);
            } else {
                return new ValueWithUnitConverter<>(BigIntegerConverter.INSTANCE);
            }
        } else {
            throw new IllegalArgumentException(type.getName());
        }
    }
}
