package io.p1jmonitor.p1processor;

import java.io.IOException;

public class SerialPortException extends IOException {
    public SerialPortException(Throwable e) {
        super(e);
    }

    public SerialPortException(String message) {
        super(message);
    }

    public SerialPortException(String message, Throwable e) {
        super(message, e);
    }
}
