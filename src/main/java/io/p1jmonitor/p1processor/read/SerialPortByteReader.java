package io.p1jmonitor.p1processor.read;

import io.p1jmonitor.p1processor.TelegramException;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static io.p1jmonitor.p1processor.TelegramException.Error.SERIAL_ERROR;

public class SerialPortByteReader extends InputStream {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerialPortByteReader.class);

    public static SerialPortByteReader create(String portName) throws IOException {
        SerialPort serialPort = new SerialPort(portName);
        try {
            LOGGER.debug("Connecting to serial port {}", serialPort.getPortName());
            if (!serialPort.openPort() && serialPort.setParams(115200, 8, 1, 0)) {
                throw new TelegramException(SERIAL_ERROR, "Could not open port "+serialPort.getPortName());
            }
        } catch (SerialPortException e) {
            LOGGER.error("Problem connection to serial port {}", e.getPortName(), e);
            throw new TelegramException(SERIAL_ERROR, e.getMessage());
        }
        return new SerialPortByteReader(serialPort);
    }

    private final SerialPort serialPort;
    private byte[] buffer;
    private int position;

    public SerialPortByteReader(SerialPort serialPort) {
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
        } catch (SerialPortException e) {
            LOGGER.error("Problem reading byte from {}", e.getPortName(), e);
            throw new TelegramException(SERIAL_ERROR, e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            LOGGER.error("Problem closing {}", e.getPortName(), e);
            throw new TelegramException(SERIAL_ERROR, e.getMessage());
        }
    }
}
