package io.p1jmonitor.telegram.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ZonedDateTimeConverterTest {

    private ZoneId zoneId = ZoneId.of("Antarctica/McMurdo");

    private ZonedDateTimeConverter zonedDateTimeConverter;

    @BeforeEach
    void setUp() {
        zonedDateTimeConverter = new ZonedDateTimeConverter(zoneId);
    }

    @Test
    void convert() {
        ZonedDateTime dateTime = zonedDateTimeConverter.convert("180820044542W");
        assertThat(dateTime).isEqualTo(ZonedDateTime.of(2018, 8, 20, 4,45,42, 0, zoneId));
    }

    @Test
    void verifyLength() {
        assertThatThrownBy(() ->zonedDateTimeConverter.convert("20180820044542W"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid length");
    }

    @Test
    void verifyDst() {
        assertThatThrownBy(() -> zonedDateTimeConverter.convert("180820044542S"))
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