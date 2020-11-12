package io.p1jmonitor.telegram.raw.io;

import io.p1jmonitor.telegram.raw.RawTelegram;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

public interface TelegramReader extends Closeable {
    Optional<RawTelegram> readTelegram() throws IOException;
}
