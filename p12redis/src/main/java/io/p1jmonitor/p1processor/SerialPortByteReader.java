package io.p1jmonitor.p1processor;

import jssc.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

public class SerialPortByteReader extends InputStream {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerialPortByteReader.class);

    public static SerialPortByteReader create(String portName, SerialConfiguration serialConfiguration) throws IOException {
        SerialPort serialPort = new SerialPort(portName);
        try {
            LOGGER.debug("Connecting to serial port {} using configuration {}", serialPort.getPortName(), serialConfiguration);
            if (!serialPort.openPort() && serialPort.setParams(serialConfiguration.getBaudRate(), serialConfiguration.getDataBits(), serialConfiguration.getStopBits(), serialConfiguration.getParity())) {
                throw new SerialPortException("Could not open port "+serialPort.getPortName());
            }
        } catch (jssc.SerialPortException e) {
            throw new SerialPortException(e);
        }
        addShutdownHook(serialPort);
        return new SerialPortByteReader(serialPort, Duration.ofSeconds(serialConfiguration.getTimeoutSeconds()), serialConfiguration.getWaitNanos());
    }

    private static void addShutdownHook(SerialPort serialPort) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (serialPort.isOpened()) {
                    LOGGER.debug("Closing serial port {} from shutdown hook", serialPort.getPortName());
                    serialPort.closePort();
                }
            } catch (jssc.SerialPortException e) {
                LOGGER.error("Could not close port {} on shutdown hook", e.getPortName(), e);
            }
        }));
    }

    private final SerialPort serialPort;
    private final Duration timeout;
    private final int waitNanos;
    private byte[] buffer;
    private int position;

    SerialPortByteReader(SerialPort serialPort, Duration timeout, int waitNanos) {
        this.serialPort = serialPort;
        this.timeout = timeout;
        this.waitNanos = waitNanos;
    }

    @Override
    public int read() throws IOException {
        try {
            long start = System.nanoTime();
            while (buffer == null || position >= buffer.length) {
                buffer = serialPort.readBytes();
                position = 0;

                if (buffer == null) {
                    if (System.nanoTime() - start > timeout.toNanos()) {
                        throw new SerialPortException("Timeout");
                    }

                    try {
                        Thread.sleep(0, waitNanos);//Need to sleep some time to prevent high CPU loading
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        throw new SerialPortException("Interrupted", ex);
                    }
                }
            }

            return buffer[position++];
        } catch (jssc.SerialPortException e) {
            throw new SerialPortException(e);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            if (serialPort.isOpened()) {
                serialPort.closePort();
            }
        } catch (jssc.SerialPortException e) {
            throw new SerialPortException(e);
        }
    }
}
