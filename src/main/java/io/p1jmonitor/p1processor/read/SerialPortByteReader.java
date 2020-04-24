package io.p1jmonitor.p1processor.read;

import io.p1jmonitor.p1processor.TelegramException;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialPortByteReader implements ByteReader {
    private static Logger LOGGER = LoggerFactory.getLogger(SerialPortByteReader.class);

    public static SerialPortByteReader create(String portName) throws TelegramException {
        SerialPort serialPort = new SerialPort(portName);
        try {
            LOGGER.debug("Connecting to serial port {}", serialPort.getPortName());
            if (!serialPort.openPort() && serialPort.setParams(115200, 8, 1, 0)) {
                throw new TelegramException(TelegramException.Error.SERIAL_ERROR, "Could not open port "+serialPort.getPortName());
            }
        } catch (SerialPortException e) {
            LOGGER.error("Problem connection to serial port {}", e.getPortName(), e);
            throw new TelegramException(TelegramException.Error.SERIAL_ERROR, e.getMessage());
        }
        return new SerialPortByteReader(serialPort);
    }

    private SerialPort serialPort;
    private byte[] buffer;
    private int position;

    public SerialPortByteReader(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public int nextByte() throws TelegramException {
        try {
            while (buffer == null || position >= buffer.length) {
                buffer = serialPort.readBytes();
                position = 0;
            }

            return buffer[position++];
        } catch (SerialPortException e) {
            LOGGER.error("Problem reading byte from {}", e.getPortName(), e);
            throw new TelegramException(TelegramException.Error.SERIAL_ERROR, "Problem reading from " + e.getPortName());
        }
    }

    @Override
    public void close() throws TelegramException {
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            LOGGER.error("Problem closing {}", e.getPortName(), e);
            throw new TelegramException(TelegramException.Error.SERIAL_ERROR, "Problem closing " + e.getPortName());
        }
    }
}
