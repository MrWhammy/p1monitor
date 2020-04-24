package io.p1jmonitor.p1processor;

import io.p1jmonitor.p1processor.publish.TelegramPublisher;
import io.p1jmonitor.p1processor.read.TelegramReader;

import java.util.Optional;
import java.util.concurrent.Callable;

public class SingleTelegramProcessor implements Callable<Boolean> {

    private final TelegramReader reader;
    private final TelegramPublisher publisher;

    public SingleTelegramProcessor(TelegramReader reader, TelegramPublisher publisher) {
        this.reader = reader;
        this.publisher = publisher;
    }

    @Override
    public Boolean call() {
        Optional<Telegram> optionalTelegram = reader.readTelegram();
        if (optionalTelegram.isPresent()) {
            publisher.publish(optionalTelegram.get());
            return true;
        } else {
            return false;
        }
    }
}

