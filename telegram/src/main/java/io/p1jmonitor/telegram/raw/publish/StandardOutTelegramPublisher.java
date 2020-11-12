package io.p1jmonitor.telegram.raw.publish;

import io.p1jmonitor.telegram.COSEMTelegram;
import io.p1jmonitor.telegram.raw.io.ReadTelegram;

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
