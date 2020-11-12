package io.p1jmonitor.telegram.converter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.zone.ZoneOffsetTransition;

public class ZonedDateTimeConverter implements ValueConverter<ZonedDateTime> {

    public enum DstSpecifier {
        WINTER,
        SUMMER;

        public static DstSpecifier fromChar(char dst) {
            switch (dst) {
                case 'S':
                    return SUMMER;
                case 'W':
                    return WINTER;
                default:
                    throw new IllegalArgumentException("Unknown DST specifier " + dst);
            }
        }
    }

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

        TemporalAccessor temporalAccessor = FORMATTER.parse(value.substring(0, 12));

        LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);

        ZonedDateTime zonedDateTime;
        DstSpecifier dst = DstSpecifier.fromChar(value.charAt(12));
        ZoneOffsetTransition transition = zoneId.getRules().getTransition(localDateTime);
        if (transition != null && transition.isOverlap()) {
            // overlap means Summer -> Winter (Fall back)
            switch (dst) {
                case SUMMER:
                    zonedDateTime = localDateTime.atZone(zoneId).withEarlierOffsetAtOverlap();
                    break;
                case WINTER:
                    zonedDateTime = localDateTime.atZone(zoneId).withLaterOffsetAtOverlap();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown DST specifier " + dst);
            }
        } else {
            zonedDateTime = localDateTime.atZone(zoneId);
        }
        if (zoneId.getRules().isDaylightSavings(zonedDateTime.toInstant()) && dst != DstSpecifier.SUMMER) {
            throw new IllegalArgumentException("Illegal DST specifier "+dst+" for "+zonedDateTime+" in "+value);
        }
        return zonedDateTime;
    }
}
