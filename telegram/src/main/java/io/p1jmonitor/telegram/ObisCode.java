package io.p1jmonitor.telegram;

import java.util.Objects;

public class ObisCode {
    public static ObisCode of(String code) {
        return new ObisCode(code);
    }

    private final String code;

    private ObisCode(String code) {
        this.code = Objects.requireNonNull(code);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObisCode obisCode = (ObisCode) o;
        return code.equals(obisCode.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
