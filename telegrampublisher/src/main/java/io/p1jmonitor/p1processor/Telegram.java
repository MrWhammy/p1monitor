package io.p1jmonitor.p1processor;

import java.util.zip.Checksum;

public class Telegram {
    private final String telegram;
    private final long checksumValue;
    private final Checksum calculatedChecksum;

    public Telegram(String telegram, long checksumValue, Checksum calculatedChecksum) {
        this.telegram = telegram;
        this.checksumValue = checksumValue;
        this.calculatedChecksum = calculatedChecksum;
    }

    public String getTelegram() {
        return telegram;
    }

    public Checksum getCalculatedChecksum() {
        return calculatedChecksum;
    }

    public long getChecksumValue() {
        return checksumValue;
    }

    public boolean isChecksumValid() {
        return checksumValue == calculatedChecksum.getValue();
    }

    @Override
    public String toString() {
        return telegram;
    }
}
