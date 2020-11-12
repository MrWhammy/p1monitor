package io.p1jmonitor.telegram.publish;

import io.p1jmonitor.telegram.COSEMTelegram;

import java.io.Closeable;
import java.io.IOException;

public interface TelegramPublisher extends Closeable {
    void publish(COSEMTelegram telegram) throws IOException;
}
