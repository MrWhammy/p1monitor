package io.p1jmonitor.telegram;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class COSEMTelegram {

    private static final String NEW_LINE = "\r\n";

    public static COSEMTelegram from(String message) {
        List<String> lines = Arrays.asList(message.split(NEW_LINE));

        if (lines.size() < 3) {
            throw new IllegalArgumentException("lines");
        }

        return new COSEMTelegram(
                new Line.Header(lines.get(0)),
                lines.subList(2, lines.size() - 1).stream().map(Line.DataLine::new).collect(Collectors.toMap(Line.DataLine::getKey, Function.identity())),
                new Line.Footer(lines.get(lines.size() - 1)));
    }

    private final Line.Header header;
    private final Map<ObisCode, Line.DataLine> dataLines;
    private final Line.Footer footer;

    private COSEMTelegram(Line.Header header, Map<ObisCode, Line.DataLine> dataLines, Line.Footer footer) {
        this.header = header;
        this.dataLines = dataLines;
        this.footer = footer;
    }

    public Line.Header getHeader() {
        return header;
    }

    public Set<ObisCode> getCodes() {
        return dataLines.keySet();
    }

    public Line.DataLine getDataLine(ObisCode obisCode) {
        return dataLines.get(obisCode);
    }

    public Line.Footer getFooter() {
        return footer;
    }

    @Override
    public String toString() {
        return Stream.concat(
                    Stream.concat(
                        Stream.of(header),
                        dataLines.values().stream()
                    ),
                    Stream.of(footer))
                .map(Line::toString)
                .map(line -> line + NEW_LINE)
                .collect(Collectors.joining());
    }
}
