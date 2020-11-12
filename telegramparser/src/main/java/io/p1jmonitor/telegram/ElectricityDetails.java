package io.p1jmonitor.telegram;

import java.math.BigDecimal;

public interface ElectricityDetails extends MeterDetails {
    MeterType TYPE = MeterType.ELECTRICITY;

    @Override
    default MeterType getMeterType() {
        return TYPE;
    }

    ValueWithUnit<BigDecimal> getElectricityDeliveredToClientTariff1();
    ValueWithUnit<BigDecimal> getElectricityDeliveredToClientTariff2();
    ValueWithUnit<BigDecimal> getElectricityDeliveredByClientTariff1();
    ValueWithUnit<BigDecimal> getElectricityDeliveredByClientTariff2();
    String getTariffIndicatorElectricity();
    ValueWithUnit<BigDecimal> getActualElectricityPowerDelivered();
    ValueWithUnit<BigDecimal> getActualElectricityPowerReceived();
}
