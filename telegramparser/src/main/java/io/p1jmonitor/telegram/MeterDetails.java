package io.p1jmonitor.telegram;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface MeterDetails {
    String getEquipmentIdentifier();
    ZonedDateTime getTimestamp();
    MeterType getMeterType();
}
