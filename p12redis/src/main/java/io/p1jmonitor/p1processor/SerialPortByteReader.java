package io.p1jmonitor.p1processor;

import jssc.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class SerialPortByteReader extends InputStream {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerialPortByteReader.class);

    public static SerialPortByteReader create(String portName) throws IOException {
        SerialPort serialPort = new SerialPort(portName);
        try {
            LOGGER.debug("Connecting to serial port {}", serialPort.getPortName());
            if (!serialPort.openPort() && serialPort.setParams(115200, 8, 1, 0)) {
                throw new SerialPortException("Could not open port "+serialPort.getPortName());
            }
        } catch (jssc.SerialPortException e) {
            throw new SerialPortException(e);
        }
        addShutdownHook(serialPort);
        return new SerialPortByteReader(serialPort);
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
    private byte[] buffer;
    private int position;

    SerialPortByteReader(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public int read() throws IOException {
        try {
            while (buffer == null || position >= buffer.length) {
                buffer = serialPort.readBytes();
                position = 0;
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
