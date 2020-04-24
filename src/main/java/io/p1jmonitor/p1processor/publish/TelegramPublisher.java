package io.p1jmonitor.p1processor.publish;

import io.p1jmonitor.p1processor.Telegram;

import java.io.Closeable;
import java.io.IOException;

public interface TelegramPublisher extends Closeable {
    void publish(Telegram telegram) throws IOException;
}
