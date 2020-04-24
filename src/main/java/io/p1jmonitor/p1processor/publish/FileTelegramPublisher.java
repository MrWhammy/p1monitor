package io.p1jmonitor.p1processor.publish;

import io.p1jmonitor.p1processor.Telegram;
import io.p1jmonitor.p1processor.TelegramException;
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
    public void publish(Telegram telegram) throws TelegramException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        Path file = fileSystem.getPath(dateTimeFormatter.format(LocalDateTime.now(clock)) + ".txt");
        try {
            Files.write(file, telegram.getTelegram().getBytes(StandardCharsets.US_ASCII));
            LOGGER.info("Wrote telegram to file {}", file);
        } catch (IOException e) {
            LOGGER.error("Problem writing to file {}", file, e);
            throw new TelegramException(TelegramException.Error.IO_ERROR, "Could not write telegram to "+ file);
        }
    }

    @Override
    public void close() throws IOException {
        this.fileSystem.close();
    }
}
