package io.p1jmonitor.publish;

import io.p1jmonitor.telegram.raw.RawTelegram;

public class StandardOutTelegramPublisher implements TelegramPublisher {
    public static StandardOutTelegramPublisher create() {
        return new StandardOutTelegramPublisher();
    }

    @Override
    public void publish(RawTelegram telegram) {
        System.out.println(telegram.getTelegram());
    }

    @Override
    public void close() {
        // no-op
    }
}
