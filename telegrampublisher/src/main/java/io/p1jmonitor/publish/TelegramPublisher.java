package io.p1jmonitor.publish;

import io.p1jmonitor.RawTelegram;

import java.io.Closeable;
import java.io.IOException;

public interface TelegramPublisher extends Closeable {
    void publish(RawTelegram telegram) throws IOException;
}
