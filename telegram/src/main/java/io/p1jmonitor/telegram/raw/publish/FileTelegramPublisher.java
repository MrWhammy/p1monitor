package io.p1jmonitor.telegram.raw.publish;

import io.p1jmonitor.telegram.raw.RawTelegram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileTelegramPublisher implements TelegramPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTelegramPublisher.class);

    public static FileTelegramPublisher create() {
        return new FileTelegramPublisher(FileSystems.getDefault(), Clock.systemDefaultZone());
    }

    private final FileSystem fileSystem;
    private final Clock clock;

    public FileTelegramPublisher(FileSystem fileSystem, Clock clock) {
        this.fileSystem = fileSystem;
        this.clock = clock;
    }

    @Override
    public void publish(RawTelegram telegram) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        Path file = fileSystem.getPath(dateTimeFormatter.format(LocalDateTime.now(clock)) + ".txt");
        Files.write(file, telegram.toString().getBytes(StandardCharsets.US_ASCII));
        LOGGER.info("Wrote telegram to file {}", file);
    }

    @Override
    public void close() throws IOException {
        try {
            if (!this.fileSystem.equals(FileSystems.getDefault())) {
                this.fileSystem.close();
            }
        } catch (UnsupportedOperationException e) {
            // no problem, but no way to check properly
        }
    }
}
