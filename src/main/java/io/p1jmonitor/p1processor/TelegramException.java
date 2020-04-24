package io.p1jmonitor.p1processor;

public class TelegramException extends RuntimeException {
    public enum Error {
        CONFIG_ERROR(1),
        SERIAL_ERROR(2),
        REDIS_ERROR(3),
        IO_ERROR(4);

        private int value;

        Error(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private final Error error;

    public TelegramException(Error error, String message) {
        super(message);
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
