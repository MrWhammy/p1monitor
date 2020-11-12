package io.p1jmonitor.telegram.publish;

import io.p1jmonitor.telegram.COSEMTelegram;

public class StandardOutTelegramPublisher implements TelegramPublisher {
    public static StandardOutTelegramPublisher create() {
        return new StandardOutTelegramPublisher();
    }

    @Override
    public void publish(COSEMTelegram telegram) {
        System.out.println(telegram);
    }

    @Override
    public void close() {
        // no-op
    }
}
