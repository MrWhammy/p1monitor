package io.p1jmonitor.telegram;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Line {
    private final Matcher line;

    public Line(Pattern pattern, String line) {
        this.line = pattern.matcher(line);
        if (!this.line.matches()) {
            throw new IllegalArgumentException(line);
        }
    }

    protected String get(String key) {
        return line.group(key);
    }

    public static class Header extends Line {
        private final static Pattern PATTERN = Pattern.compile("^/(?<XXX>.{3})5(?<Ident>.+)$");

        Header(String line) {
            super(PATTERN, line);
        }

        public String getXXX() {
            return get("XXX");
        }

        public String getIdent() {
            return get("Ident");
        }
    }

    public static class DataLine extends Line {
        private final static Pattern PATTERN = Pattern.compile("^(?<key>[0-9:\\-.]+)(?<value>\\(.*\\))$");
        private final static Pattern VALUE_PATTERN = Pattern.compile("\\((?<value>[^)]*)\\)");

        DataLine(String line) {
            super(PATTERN, line);
        }

        public ObisCode getKey() {
            return ObisCode.of(get("key"));
        }

        public List<String> getValues() {
            Matcher valueMatcher = VALUE_PATTERN.matcher(get("value"));
            List<String> values = new ArrayList<>();
            while (valueMatcher.find()) {
                values.add(valueMatcher.group("value"));
            }
            return values;
        }
    }

    public static class Footer extends Line {
        private final static Pattern PATTERN = Pattern.compile("^!(?<CRC>.+)$");

        Footer(String line) {
            super(PATTERN, line);
        }

        public String getCRC() {
            return get("CRC");
        }
    }
}
