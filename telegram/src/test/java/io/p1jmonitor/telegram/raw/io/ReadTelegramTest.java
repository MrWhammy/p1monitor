package io.p1jmonitor.telegram.raw.io;


import io.p1jmonitor.telegram.COSEMTelegram;
import io.p1jmonitor.telegram.Line;
import io.p1jmonitor.telegram.raw.io.ReadTelegram;
import org.junit.jupiter.api.Test;

import java.util.zip.Checksum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReadTelegramTest {
    @Test
    void getters() {
        Checksum checksum = mock(Checksum.class);
        COSEMTelegram cosemTelegram = mock(COSEMTelegram.class);
        ReadTelegram telegram = new ReadTelegram(cosemTelegram, checksum);
        assertThat(telegram.getTelegram()).isEqualTo(cosemTelegram);
        assertThat(telegram.getCalculatedChecksum()).isEqualTo(checksum);
    }

    @Test
    void valid() {
        Checksum checksum = mock(Checksum.class);
        when(checksum.getValue()).thenReturn(666L);
        COSEMTelegram cosemTelegram = mock(COSEMTelegram.class);
        Line.Footer footer = mock(Line.Footer.class);
        when(footer.getChecksum()).thenReturn(666L);
        when(cosemTelegram.getFooter()).thenReturn(footer);
        ReadTelegram telegram = new ReadTelegram(cosemTelegram, checksum);
        assertThat(telegram.isChecksumValid()).isTrue();
    }

    @Test
    void invalid() {
        Checksum checksum = mock(Checksum.class);
        when(checksum.getValue()).thenReturn(666L);
        COSEMTelegram cosemTelegram = mock(COSEMTelegram.class);
        Line.Footer footer = mock(Line.Footer.class);
        when(footer.getChecksum()).thenReturn(667L);
        when(cosemTelegram.getFooter()).thenReturn(footer);
        ReadTelegram telegram = new ReadTelegram(cosemTelegram, checksum);
        assertThat(telegram.isChecksumValid()).isFalse();
    }
}