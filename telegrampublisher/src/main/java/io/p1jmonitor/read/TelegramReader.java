package io.p1jmonitor.read;

import io.p1jmonitor.RawTelegram;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

public interface TelegramReader extends Closeable {
    Optional<RawTelegram> readTelegram() throws IOException;
}
