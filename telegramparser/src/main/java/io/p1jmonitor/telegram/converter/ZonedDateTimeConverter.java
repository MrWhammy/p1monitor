package io.p1jmonitor.telegram.converter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class ZonedDateTimeConverter implements ValueConverter<ZonedDateTime> {

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    private final ZoneId zoneId;

    public ZonedDateTimeConverter(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public ZonedDateTime convert(String value) {
        if (value == null) {
            throw new NullPointerException("value");
        }

        if (value.length() != 13) {
            throw new IllegalArgumentException("Invalid length for "+value+", expected 13");
        }

        char dst = value.charAt(12);
        TemporalAccessor temporalAccessor = FORMATTER.parse(value.substring(0, 12));

        LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        if (!verifyDst(zonedDateTime, dst)) {
            throw new IllegalArgumentException("Illegal DST specifier "+dst+" for "+zonedDateTime+" in "+value);
        }
        return zonedDateTime;
    }

    private boolean verifyDst(ZonedDateTime zonedDateTime, char dst) {
        Instant instant = zonedDateTime.toInstant();
        boolean dstBool = zoneId.getRules().isDaylightSavings(instant);
        switch (dst) {
            case 'S':
                return dstBool;
            case 'W':
                return !dstBool;
            default:
                throw new IllegalArgumentException("Unknown DST specifier "+dst);
        }
    }
}
