package io.p1jmonitor.p1processor;

import io.p1jmonitor.RawTelegram;
import org.junit.jupiter.api.Test;

import java.util.zip.Checksum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RawTelegramTest {
    @Test
    void getters() {
        Checksum checksum = mock(Checksum.class);
        RawTelegram telegram = new RawTelegram("CONTENT", 666L, checksum);
        assertThat(telegram.getTelegram()).isEqualTo("CONTENT");
        assertThat(telegram.getChecksumValue()).isEqualTo(666L);
        assertThat(telegram.getCalculatedChecksum()).isEqualTo(checksum);
    }

    @Test
    void valid() {
        Checksum checksum = mock(Checksum.class);
        when(checksum.getValue()).thenReturn(666L);
        RawTelegram telegram = new RawTelegram("CONTENT", 666L, checksum);
        assertThat(telegram.isChecksumValid()).isTrue();
    }

    @Test
    void invalid() {
        Checksum checksum = mock(Checksum.class);
        when(checksum.getValue()).thenReturn(667L);
        RawTelegram telegram = new RawTelegram("CONTENT", 666L, checksum);
        assertThat(telegram.isChecksumValid()).isFalse();
    }
}