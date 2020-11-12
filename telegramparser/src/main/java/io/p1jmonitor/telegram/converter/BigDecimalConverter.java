package io.p1jmonitor.telegram.converter;

import java.math.BigDecimal;

public enum BigDecimalConverter implements ValueConverter<BigDecimal> {
    INSTANCE;

    @Override
    public BigDecimal convert(String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }

        return new BigDecimal(value);
    }
}
