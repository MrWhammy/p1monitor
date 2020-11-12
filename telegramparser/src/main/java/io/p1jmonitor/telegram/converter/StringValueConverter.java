package io.p1jmonitor.telegram.converter;

public enum StringValueConverter implements ValueConverter<String> {
    INSTANCE;

    @Override
    public String convert(String value) {
        return value;
    }
}
