package io.p1jmonitor.telegram;

import java.time.LocalDateTime;
import java.util.List;

public interface Telegram {
    String getEquipmentIdentifier();
    LocalDateTime getTimestamp();
    String getTextMessage();

    List<MeterDetails> getMeterDetails();
}
