package io.p1jmonitor.telegram;

import java.math.BigDecimal;

public interface GasDetails extends MeterDetails {
    MeterType TYPE = MeterType.GAS;

    @Override
    default MeterType getMeterType() {
        return TYPE;
    }

    ValueWithUnit<BigDecimal> getGasDeliveredLastFiveMinute();
}
