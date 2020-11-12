package io.p1jmonitor.telegram;

import java.time.ZonedDateTime;
import java.util.List;

public interface Telegram {
    String getEquipmentIdentifier();
    ZonedDateTime getTimestamp();
    String getTextMessage();

    List<MeterDetails> getMeterDetails();
}
