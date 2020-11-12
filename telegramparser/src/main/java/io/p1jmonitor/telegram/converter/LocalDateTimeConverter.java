package io.p1jmonitor.telegram.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public enum LocalDateTimeConverter implements ValueConverter<LocalDateTime> {
    INSTANCE;

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    @Override
    public LocalDateTime convert(String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }

        if (value.length() != 13) {
            throw new IllegalArgumentException(value);
        }

        char dst = value.charAt(12);
        TemporalAccessor temporalAccessor = FORMATTER.parse(value.substring(0, 12));
        // TODO: check dst
        return LocalDateTime.from(temporalAccessor);
    }
}
