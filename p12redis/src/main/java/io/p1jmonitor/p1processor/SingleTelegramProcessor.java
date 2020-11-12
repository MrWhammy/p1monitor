package io.p1jmonitor.p1processor;

import io.p1jmonitor.telegram.io.ReadTelegram;
import io.p1jmonitor.telegram.publish.TelegramPublisher;
import io.p1jmonitor.telegram.io.TelegramReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Callable;

public class SingleTelegramProcessor implements Callable<Boolean> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleTelegramProcessor.class);

    private final TelegramReader reader;
    private final TelegramPublisher publisher;

    public SingleTelegramProcessor(TelegramReader reader, TelegramPublisher publisher) {
        this.reader = reader;
        this.publisher = publisher;
    }

    @Override
    public Boolean call() throws IOException {
        Optional<ReadTelegram> optionalTelegram = reader.readTelegram().filter(this::validateChecksum);
        if (optionalTelegram.isPresent()) {
            publisher.publish(optionalTelegram.get().getTelegram());
            return true;
        } else {
            return false;
        }
    }

    private boolean validateChecksum(ReadTelegram telegram) {
        if (!telegram.isChecksumValid()) {
            LOGGER.error("Telegram {} has invalid checksum", telegram);
            return false;
        } else {
            return true;
        }
    }
}

