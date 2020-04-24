package io.p1jmonitor.p1processor.read;

import io.p1jmonitor.p1processor.Telegram;
import io.p1jmonitor.p1processor.TelegramException;

import java.util.Optional;

public interface TelegramReader extends AutoCloseable {
    Optional<Telegram> readTelegram() throws TelegramException;
}
