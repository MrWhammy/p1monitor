package io.p1jmonitor.telegram;

import java.time.LocalDateTime;

public interface MeterDetails {
    String getEquipmentIdentifier();
    LocalDateTime getTimestamp();
    MeterType getMeterType();
}
