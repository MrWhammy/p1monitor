package io.p1jmonitor.p1processor.read;

import io.p1jmonitor.p1processor.RawTelegram;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

public interface TelegramReader extends Closeable {
    Optional<RawTelegram> readTelegram() throws IOException;
}
