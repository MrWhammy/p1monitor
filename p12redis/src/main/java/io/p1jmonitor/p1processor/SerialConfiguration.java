package io.p1jmonitor.p1processor;

import jssc.SerialPort;

import java.util.Properties;

public class SerialConfiguration {
    private static final String PREFIX = "serial.";
    private static final String TIMEOUT_KEY = PREFIX + "timeoutSeconds";
    private static final String WAITNANOS_KEY = PREFIX + "waitNanos";
    private static final String BAUDRATE_KEY = PREFIX + "baudRate";
    private static final String DATABITS_KEY = PREFIX + "dataBits";
    private static final String STOPBITS_KEY = PREFIX + "stopBits";
    private static final String PARITY_KEY = PREFIX + "parity";

    public static SerialConfiguration load(Properties properties) {
        SerialConfiguration configuration = new SerialConfiguration();
        if (properties.containsKey(TIMEOUT_KEY)) {
            configuration.setTimeoutSeconds(Integer.parseInt(properties.getProperty(TIMEOUT_KEY)));
        }
        if (properties.containsKey(WAITNANOS_KEY)) {
            configuration.setWaitNanos(Integer.parseInt(properties.getProperty(WAITNANOS_KEY)));
        }
        if (properties.containsKey(BAUDRATE_KEY)) {
            configuration.setBaudRate(Integer.parseInt(properties.getProperty(BAUDRATE_KEY)));
        }
        if (properties.containsKey(DATABITS_KEY)) {
            configuration.setDataBits(Integer.parseInt(properties.getProperty(DATABITS_KEY)));
        }
        if (properties.containsKey(STOPBITS_KEY)) {
            configuration.setStopBits(Integer.parseInt(properties.getProperty(STOPBITS_KEY)));
        }
        if (properties.containsKey(PARITY_KEY)) {
            configuration.setParity(Integer.parseInt(properties.getProperty(PARITY_KEY)));
        }
        return configuration;
    }

    private int timeoutSeconds = 5;
    private int waitNanos = 100;

    private int baudRate = SerialPort.BAUDRATE_115200;
    private int dataBits = SerialPort.DATABITS_8;
    private int stopBits = SerialPort.STOPBITS_1;
    private int parity = SerialPort.PARITY_NONE;

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public int getWaitNanos() {
        return waitNanos;
    }

    public void setWaitNanos(int waitNanos) {
        this.waitNanos = waitNanos;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public void setStopBits(int stopBits) {
        this.stopBits = stopBits;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    @Override
    public String toString() {
        return "SerialConfiguration{" +
                "timeoutSeconds=" + timeoutSeconds +
                ", waitNanos=" + waitNanos +
                ", baudRate=" + baudRate +
                ", dataBits=" + dataBits +
                ", stopBits=" + stopBits +
                ", parity=" + parity +
                '}';
    }
}
