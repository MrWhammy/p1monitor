package io.p1jmonitor.telegram.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ZonedDateTimeConverterTest {

    private ZoneId zoneId = ZoneId.of("Europe/Brussels");

    private ZonedDateTimeConverter zonedDateTimeConverter;

    @BeforeEach
    void setUp() {
        zonedDateTimeConverter = new ZonedDateTimeConverter(zoneId);
    }

    @Test
    void convert() {
        ZonedDateTime summerDate = zonedDateTimeConverter.convert("201025023000S");
        ZonedDateTime winterDate = zonedDateTimeConverter.convert("201025023000W");
        assertThat(Duration.between(summerDate, winterDate)).isEqualTo(Duration.ofHours(1));
    }

    @Test
    void verifyLength() {
        assertThatThrownBy(() ->zonedDateTimeConverter.convert("20180820044542S"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid length");
    }

    @Test
    void verifyDst() {
        assertThatThrownBy(() -> zonedDateTimeConverter.convert("180820044542W"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Illegal DST specifier");
    }

    @Test
    void verifyDstSpecifier() {
        assertThatThrownBy(() -> zonedDateTimeConverter.convert("180820044542A"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown DST specifier");
    }
}