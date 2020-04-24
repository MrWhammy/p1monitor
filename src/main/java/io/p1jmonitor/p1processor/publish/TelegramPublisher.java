package io.p1jmonitor.p1processor.publish;

import io.p1jmonitor.p1processor.Telegram;
import io.p1jmonitor.p1processor.TelegramException;

public interface TelegramPublisher extends AutoCloseable {
    void publish(Telegram telegram) throws TelegramException;
}
