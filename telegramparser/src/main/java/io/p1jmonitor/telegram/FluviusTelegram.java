package io.p1jmonitor.telegram;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class FluviusTelegram implements Telegram {

    private String equipmentIdentifier;
    private ZonedDateTime timestamp;
    private ValueWithUnit<BigDecimal> electricityDeliveredToClientTariff1;
    private ValueWithUnit<BigDecimal> electricityDeliveredToClientTariff2;
    private ValueWithUnit<BigDecimal> electricityDeliveredByClientTariff1;
    private ValueWithUnit<BigDecimal> electricityDeliveredByClientTariff2;
    private String tariffIndicatorElectricity;
    private ValueWithUnit<BigDecimal> actualElectricityPowerDelivered;
    private ValueWithUnit<BigDecimal> actualElectricityPowerReceived;
    private ValueWithUnit<BigDecimal> instantaneousVoltageL1;
    private ValueWithUnit<BigDecimal> instantaneousVoltageL2;
    private ValueWithUnit<BigDecimal> instantaneousVoltageL3;
    private ValueWithUnit<BigInteger> instantaneousCurrentL1;
    private ValueWithUnit<BigInteger> instantaneousCurrentL2;
    private ValueWithUnit<BigInteger> instantaneousCurrentL3;
    private String textMessage;
    private BigInteger gasDeviceType;
    private String gasEquipmentIdentifier;
    private ValueWithUnit<BigDecimal> gasDeliveredLastFiveMinute;
    private ZonedDateTime gasDeliveredTimestamp;

    @Override
    @ObisReference("0-0:96.1.1")
    public String getEquipmentIdentifier() {
        return equipmentIdentifier;
    }

    public void setEquipmentIdentifier(String equipmentIdentifier) {
        this.equipmentIdentifier = equipmentIdentifier;
    }

    @Override
    @ObisReference("0-0:1.0.0")
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @ObisReference(value = "1-0:1.8.1", minimumDecimals = 3, maximumDecimals = 3)
    public ValueWithUnit<BigDecimal> getElectricityDeliveredToClientTariff1() {
        return electricityDeliveredToClientTariff1;
    }

    public void setElectricityDeliveredToClientTariff1(ValueWithUnit<BigDecimal> electricityDeliveredToClientTariff1) {
        this.electricityDeliveredToClientTariff1 = electricityDeliveredToClientTariff1;
    }

    @ObisReference(value = "1-0:1.8.2", minimumDecimals = 3, maximumDecimals = 3)
    public ValueWithUnit<BigDecimal> getElectricityDeliveredToClientTariff2() {
        return electricityDeliveredToClientTariff2;
    }

    public void setElectricityDeliveredToClientTariff2(ValueWithUnit<BigDecimal> electricityDeliveredToClientTariff2) {
        this.electricityDeliveredToClientTariff2 = electricityDeliveredToClientTariff2;
    }

    @ObisReference(value = "1-0:2.8.1", minimumDecimals = 3, maximumDecimals = 3)
    public ValueWithUnit<BigDecimal> getElectricityDeliveredByClientTariff1() {
        return electricityDeliveredByClientTariff1;
    }

    public void setElectricityDeliveredByClientTariff1(ValueWithUnit<BigDecimal> electricityDeliveredByClientTariff1) {
        this.electricityDeliveredByClientTariff1 = electricityDeliveredByClientTariff1;
    }

    @ObisReference(value = "1-0:2.8.2", minimumDecimals = 3, maximumDecimals = 3)
    public ValueWithUnit<BigDecimal> getElectricityDeliveredByClientTariff2() {
        return electricityDeliveredByClientTariff2;
    }

    public void setElectricityDeliveredByClientTariff2(ValueWithUnit<BigDecimal> electricityDeliveredByClientTariff2) {
        this.electricityDeliveredByClientTariff2 = electricityDeliveredByClientTariff2;
    }

    @ObisReference(value = "0-0:96.14.0")
    public String getTariffIndicatorElectricity() {
        return tariffIndicatorElectricity;
    }

    public void setTariffIndicatorElectricity(String tariffIndicatorElectricity) {
        this.tariffIndicatorElectricity = tariffIndicatorElectricity;
    }

    @ObisReference(value = "1-0:1.7.0", minimumDecimals = 3, maximumDecimals = 3)
    public ValueWithUnit<BigDecimal> getActualElectricityPowerDelivered() {
        return actualElectricityPowerDelivered;
    }

    public void setActualElectricityPowerDelivered(ValueWithUnit<BigDecimal> actualElectricityPowerDelivered) {
        this.actualElectricityPowerDelivered = actualElectricityPowerDelivered;
    }

    @ObisReference(value = "1-0:2.7.0", minimumDecimals = 3, maximumDecimals = 3)
    public ValueWithUnit<BigDecimal> getActualElectricityPowerReceived() {
        return actualElectricityPowerReceived;
    }

    public void setActualElectricityPowerReceived(ValueWithUnit<BigDecimal> actualElectricityPowerReceived) {
        this.actualElectricityPowerReceived = actualElectricityPowerReceived;
    }

    @ObisReference(value = "1-0:32.7.0", minimumDecimals = 1, maximumDecimals = 1)
    public ValueWithUnit<BigDecimal> getInstantaneousVoltageL1() {
        return instantaneousVoltageL1;
    }

    public void setInstantaneousVoltageL1(ValueWithUnit<BigDecimal> instantaneousVoltageL1) {
        this.instantaneousVoltageL1 = instantaneousVoltageL1;
    }

    @ObisReference(value = "1-0:52.7.0", minimumDecimals = 1, maximumDecimals = 1)
    public ValueWithUnit<BigDecimal> getInstantaneousVoltageL2() {
        return instantaneousVoltageL2;
    }

    public void setInstantaneousVoltageL2(ValueWithUnit<BigDecimal> instantaneousVoltageL2) {
        this.instantaneousVoltageL2 = instantaneousVoltageL2;
    }

    @ObisReference(value = "1-0:72.7.0", minimumDecimals = 1, maximumDecimals = 1)
    public ValueWithUnit<BigDecimal> getInstantaneousVoltageL3() {
        return instantaneousVoltageL3;
    }

    public void setInstantaneousVoltageL3(ValueWithUnit<BigDecimal> instantaneousVoltageL3) {
        this.instantaneousVoltageL3 = instantaneousVoltageL3;
    }

    @ObisReference("1-0:31.7.0")
    public ValueWithUnit<BigInteger> getInstantaneousCurrentL1() {
        return instantaneousCurrentL1;
    }

    public void setInstantaneousCurrentL1(ValueWithUnit<BigInteger> instantaneousCurrentL1) {
        this.instantaneousCurrentL1 = instantaneousCurrentL1;
    }

    @ObisReference("1-0:51.7.0")
    public ValueWithUnit<BigInteger> getInstantaneousCurrentL2() {
        return instantaneousCurrentL2;
    }

    public void setInstantaneousCurrentL2(ValueWithUnit<BigInteger> instantaneousCurrentL2) {
        this.instantaneousCurrentL2 = instantaneousCurrentL2;
    }

    @ObisReference("1-0:71.7.0")
    public ValueWithUnit<BigInteger> getInstantaneousCurrentL3() {
        return instantaneousCurrentL3;
    }

    public void setInstantaneousCurrentL3(ValueWithUnit<BigInteger> instantaneousCurrentL3) {
        this.instantaneousCurrentL3 = instantaneousCurrentL3;
    }

    public ElectricityDetails getElectricityDetails() {
        return new ElectricityDetails() {
            @Override
            public ValueWithUnit<BigDecimal> getElectricityDeliveredToClientTariff1() {
                return FluviusTelegram.this.getElectricityDeliveredToClientTariff1();
            }

            @Override
            public ValueWithUnit<BigDecimal> getElectricityDeliveredToClientTariff2() {
                return FluviusTelegram.this.getElectricityDeliveredToClientTariff2();
            }

            @Override
            public ValueWithUnit<BigDecimal> getElectricityDeliveredByClientTariff1() {
                return FluviusTelegram.this.getElectricityDeliveredByClientTariff1();
            }

            @Override
            public ValueWithUnit<BigDecimal> getElectricityDeliveredByClientTariff2() {
                return FluviusTelegram.this.getElectricityDeliveredByClientTariff2();
            }

            @Override
            public String getTariffIndicatorElectricity() {
                return FluviusTelegram.this.getTariffIndicatorElectricity();
            }

            @Override
            public ValueWithUnit<BigDecimal> getActualElectricityPowerDelivered() {
                return FluviusTelegram.this.getActualElectricityPowerDelivered();
            }

            @Override
            public ValueWithUnit<BigDecimal> getActualElectricityPowerReceived() {
                return FluviusTelegram.this.getActualElectricityPowerReceived();
            }

            @Override
            public String getEquipmentIdentifier() {
                return FluviusTelegram.this.getEquipmentIdentifier();
            }

            @Override
            public ZonedDateTime getTimestamp() {
                return FluviusTelegram.this.getTimestamp();
            }
        };
    }

    @Override
    @ObisReference("0-0:96.13.0")
    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    @ObisReference("0-1:24.1.0")
    public BigInteger getGasDeviceType() {
        return gasDeviceType;
    }

    public void setGasDeviceType(BigInteger gasDeviceType) {
        this.gasDeviceType = gasDeviceType;
    }

    @ObisReference("0-1:96.1.1")
    public String getGasEquipmentIdentifier() {
        return gasEquipmentIdentifier;
    }

    public void setGasEquipmentIdentifier(String gasEquipmentIdentifier) {
        this.gasEquipmentIdentifier = gasEquipmentIdentifier;
    }

    @ObisReference(value = "0-1:24.2.3", valueIndex = 1, minimumDecimals = 3, maximumDecimals = 3)
    public ValueWithUnit<BigDecimal> getGasDeliveredLastFiveMinute() {
        return gasDeliveredLastFiveMinute;
    }

    public void setGasDeliveredLastFiveMinute(ValueWithUnit<BigDecimal> gasDeliveredLastFiveMinute) {
        this.gasDeliveredLastFiveMinute = gasDeliveredLastFiveMinute;
    }

    @ObisReference(value = "0-1:24.2.3", valueIndex = 0)
    public ZonedDateTime getGasDeliveredTimestamp() {
        return gasDeliveredTimestamp;
    }

    public void setGasDeliveredTimestamp(ZonedDateTime gasDeliveredTimestamp) {
        this.gasDeliveredTimestamp = gasDeliveredTimestamp;
    }

    public GasDetails getGasDetails() {
        return new GasDetails() {
            @Override
            public String getEquipmentIdentifier() {
                return FluviusTelegram.this.getGasEquipmentIdentifier();
            }

            @Override
            public ZonedDateTime getTimestamp() {
                return FluviusTelegram.this.getGasDeliveredTimestamp();
            }

            @Override
            public ValueWithUnit<BigDecimal> getGasDeliveredLastFiveMinute() {
                return FluviusTelegram.this.getGasDeliveredLastFiveMinute();
            }
        };
    }

    @Override
    public List<MeterDetails> getMeterDetails() {
        return Arrays.asList(getElectricityDetails(), getGasDetails());
    }
}
