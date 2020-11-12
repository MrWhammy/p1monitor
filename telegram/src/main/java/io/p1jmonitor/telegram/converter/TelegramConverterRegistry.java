package io.p1jmonitor.telegram.converter;

import io.p1jmonitor.telegram.COSEMTelegram;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TelegramConverterRegistry {
    private final Map<String, TelegramConverter> telegramConverterMap;

    public TelegramConverterRegistry(List<TelegramConverter> telegramConverters) {
        telegramConverterMap = telegramConverters.stream().collect(Collectors.toMap(TelegramConverter::getIdentifier, Function.identity()));
    }

    public TelegramConverter getConverter(COSEMTelegram cosemTelegram) {
        return telegramConverterMap.get(cosemTelegram.getHeader().getXXX());
    }
}
