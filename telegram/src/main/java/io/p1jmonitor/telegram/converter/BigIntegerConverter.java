package io.p1jmonitor.telegram.converter;

import java.math.BigInteger;

public enum BigIntegerConverter implements ValueConverter<BigInteger> {
    INSTANCE;

    @Override
    public BigInteger convert(String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }

        return new BigInteger(value);
    }
}
