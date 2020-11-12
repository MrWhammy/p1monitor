package io.p1jmonitor.p1processor.publish;

import com.google.common.jimfs.Jimfs;
import io.p1jmonitor.p1processor.Telegram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.zip.Checksum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
        Telegram telegram = new Telegram("CONTENT", -1, mock(Checksum.class));
        fileTelegramPublisher.publish(telegram);
        assertThat(jimfs.getPath("180820044542.txt")).hasContent("CONTENT");
    }

    @Test
    void close() throws IOException {
        fileTelegramPublisher.close();
        assertThat(jimfs.isOpen()).isFalse();
    }
}