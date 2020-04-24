package io.p1jmonitor.p1processor.read;

import io.p1jmonitor.p1processor.TelegramException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamByteReader implements ByteReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputStreamByteReader.class);

    private final InputStream inputStream;

    public InputStreamByteReader(InputStream inputStream) {
        this.inputStream = new BufferedInputStream(inputStream);
    }

    @Override
    public int nextByte() throws TelegramException {
        try {
            return inputStream.read();
        } catch (IOException e) {
            LOGGER.error("Problem reading from inputstream", e);
            throw new TelegramException(TelegramException.Error.IO_ERROR, "Could not read from inputstream");
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
