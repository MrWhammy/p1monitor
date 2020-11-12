package io.p1jmonitor.telegram;

public enum Unit {
        V("V"),
        A("A"),
        KW("kW"),
        KWH("kWh"),
        M3("m3"),
        GJ("GJ");

        private final String value;

        Unit(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
