package io.p1jmonitor.p1processor.publish;

import io.p1jmonitor.p1processor.RawTelegram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompositeTelegramPublisher implements TelegramPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompositeTelegramPublisher.class);

    private final List<TelegramPublisher> telegramPublisherList;

    public CompositeTelegramPublisher(List<TelegramPublisher> telegramPublisherList) {
        this.telegramPublisherList = telegramPublisherList;
    }

    @Override
    public void publish(RawTelegram telegram) throws IOException {
        for (TelegramPublisher publisher : telegramPublisherList) {
            publisher.publish(telegram);
        }
    }

    @Override
    public void close() throws IOException {
        List<Exception> thrownExceptions = new ArrayList<>();
        for (TelegramPublisher publisher : telegramPublisherList) {
            try {
                publisher.close();
            } catch (Exception ex) {
                LOGGER.error("Problem closing {}", publisher, ex);
                thrownExceptions.add(ex);
            }
        }

        if (!thrownExceptions.isEmpty()) {
            throw new IOException("Problem closing publishers");
        }
    }
}
