package io.p1jmonitor.telegram.converter;

public interface ValueConverter<T> {
    T convert(String value);
}
