package io.p1jmonitor.p1processor.read;

import io.p1jmonitor.p1processor.Telegram;
import io.p1jmonitor.p1processor.TelegramException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.zip.Checksum;

public class ByteTelegramReader implements TelegramReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ByteTelegramReader.class);

    private static final int START_CHAR = '/';
    private static final int STOP_CHAR = '!';
    private static final int CR = '\r';
    private static final int LF = '\n';
    private static final int EOF = -1;

    private final ByteReader byteReader;

    public ByteTelegramReader(ByteReader byteReader) {
        this.byteReader = byteReader;
    }

    @Override
    public Optional<Telegram> readTelegram() throws TelegramException {
        StringBuilder builder = new StringBuilder();

        // skip to /
        int currentByte = byteReader.nextByte();
        if (currentByte == EOF) {
            return Optional.empty();
        }
        while (currentByte != START_CHAR) {
            currentByte = byteReader.nextByte();
            if (currentByte == EOF) {
                return Optional.empty();
            }
        }

        Checksum checksum = new CRC16();
        // read to !
        while (currentByte != STOP_CHAR) {
            builder.append((char) currentByte);
            checksum.update(currentByte);
            currentByte = byteReader.nextByte();
            if (currentByte == EOF) {
                return Optional.empty();
            }
        }

        // add stop char and calculate checksum
        builder.append((char) currentByte);
        checksum.update(currentByte);
        currentByte = byteReader.nextByte();
        if (currentByte == EOF) {
            return Optional.empty();
        }

        long calculatedChecksum = checksum.getValue();

        long declaredChecksum = 0;
        // read until CRLF
        while (currentByte != CR) {
            builder.append((char) currentByte);
            declaredChecksum = (declaredChecksum << 4) + hex(currentByte);
            currentByte = byteReader.nextByte();
            if (currentByte == EOF) {
                return Optional.empty();
            }
        }

        builder.append((char) currentByte);
        currentByte = byteReader.nextByte();
        if (currentByte == EOF) {
            return Optional.empty();
        } else if (currentByte != LF) {
            LOGGER.trace("{} instead of LF after reading {}", currentByte, builder);
            throw new TelegramException(TelegramException.Error.IO_ERROR, "Expected LF, not " + currentByte);
        }
        builder.append((char) currentByte);

        return Optional.of(new Telegram(builder.toString(), declaredChecksum, calculatedChecksum));
    }

    @Override
    public void close() throws TelegramException {
        try {
            byteReader.close();
        } catch (Exception e) {
            LOGGER.error("Problem closing bytereader", e);
            throw new TelegramException(TelegramException.Error.IO_ERROR, "Problem reading telegram");
        }
    }

    private static int hex(int character) throws TelegramException {
        if (character >= '0' && character <= '9') {
            return character - '0';
        } else if (character >= 'A' && character <= 'F') {
            return (character - 'A') + 10;
        } else {
            throw new TelegramException(TelegramException.Error.IO_ERROR, "Expected hex char, not 0x" + Integer.toHexString(character));
        }
    }
}
