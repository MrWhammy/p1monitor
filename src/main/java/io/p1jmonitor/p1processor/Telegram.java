package io.p1jmonitor.p1processor;

public class Telegram {
    private final String telegram;
    private final long checksumValue;
    private final long calculatedChecksum;

    public Telegram(String telegram, long checksumValue, long calculatedChecksum) {
        this.telegram = telegram;
        this.checksumValue = checksumValue;
        this.calculatedChecksum = calculatedChecksum;
    }

    public String getTelegram() {
        return telegram;
    }

    public long getCalculatedChecksum() {
        return calculatedChecksum;
    }

    public long getChecksumValue() {
        return checksumValue;
    }

    public boolean isChecksumValid() {
        return checksumValue == calculatedChecksum;
    }

    @Override
    public String toString() {
        return telegram;
    }
}
