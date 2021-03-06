package io.p1jmonitor.telegram.publish;

import com.google.common.jimfs.Jimfs;
import io.p1jmonitor.telegram.COSEMTelegram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileTelegramPublisherTest {

    private final FileSystem jimfs = Jimfs.newFileSystem();
    private FileTelegramPublisher fileTelegramPublisher;

    @BeforeEach
    void setUp() {
        Instant instant = Instant.parse("2018-08-19T16:45:42.00Z");
        ZoneId zoneId = ZoneId.of("Antarctica/McMurdo");
        Clock clock = Clock.fixed(instant, zoneId);
        fileTelegramPublisher = new FileTelegramPublisher(jimfs, clock);
    }

    @Test
    void publish() throws IOException {
        COSEMTelegram cosemTelegram = mock(COSEMTelegram.class);
        when(cosemTelegram.toString()).thenReturn("CONTENT");
        fileTelegramPublisher.publish(cosemTelegram);
        assertThat(jimfs.getPath("180820044542.txt")).hasContent("CONTENT");
    }

    @Test
    void close() throws IOException {
        fileTelegramPublisher.close();
        assertThat(jimfs.isOpen()).isFalse();
    }
}