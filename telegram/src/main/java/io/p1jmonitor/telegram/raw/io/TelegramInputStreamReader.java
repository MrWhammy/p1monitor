package io.p1jmonitor.telegram.raw.io;

import io.p1jmonitor.telegram.COSEMTelegram;
import io.p1jmonitor.telegram.raw.CRC16;
import io.p1jmonitor.telegram.raw.RawTelegram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.zip.Checksum;

public class TelegramInputStreamReader implements TelegramReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramInputStreamReader.class);

    private static final int START_CHAR = '/';
    private static final int STOP_CHAR = '!';
    private static final int CR = '\r';
    private static final int LF = '\n';
    private static final int EOF = -1;

    private final InputStream inputStream;

    public TelegramInputStreamReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Optional<RawTelegram> readTelegram() throws IOException {
        StringBuilder builder = new StringBuilder();

        LOGGER.debug("Waiting for start char");
        // skip to /
        int currentByte = inputStream.read();
        if (currentByte == EOF) {
            return Optional.empty();
        }
        while (currentByte != START_CHAR) {
            currentByte = inputStream.read();
            if (currentByte == EOF) {
                return Optional.empty();
            }
        }

        LOGGER.debug("Reading telegram");
        Checksum checksum = new CRC16();
        // read to !
        while (currentByte != STOP_CHAR) {
            builder.append((char) currentByte);
            checksum.update(currentByte);
            currentByte = inputStream.read();
            if (currentByte == EOF) {
                return Optional.empty();
            }
        }

        // add stop char and calculate checksum
        builder.append((char) currentByte);
        checksum.update(currentByte);
        currentByte = inputStream.read();
        if (currentByte == EOF) {
            return Optional.empty();
        }

        // read until CRLF
        while (currentByte != CR) {
            builder.append((char) currentByte);
            currentByte = inputStream.read();
            if (currentByte == EOF) {
                return Optional.empty();
            }
        }

        builder.append((char) currentByte);
        currentByte = inputStream.read();
        if (currentByte == EOF) {
            return Optional.empty();
        } else if (currentByte != LF) {
            LOGGER.trace("{} instead of LF after reading {}", currentByte, builder);
            throw new IOException("Expected LF, not " + currentByte);
        }
        builder.append((char) currentByte);

        return Optional.of(new RawTelegram(COSEMTelegram.from(builder.toString()), checksum));
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
