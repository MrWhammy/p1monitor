package io.p1jmonitor.p1processor.publish;

import io.p1jmonitor.p1processor.RawTelegram;

import java.io.Closeable;
import java.io.IOException;

public interface TelegramPublisher extends Closeable {
    void publish(RawTelegram telegram) throws IOException;
}
