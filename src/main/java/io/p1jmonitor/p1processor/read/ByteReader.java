package io.p1jmonitor.p1processor.read;

import io.p1jmonitor.p1processor.TelegramException;

public interface ByteReader extends AutoCloseable {
    int nextByte() throws TelegramException;
}
