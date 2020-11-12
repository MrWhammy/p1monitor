package io.p1jmonitor.telegram.converter;

import io.p1jmonitor.telegram.COSEMTelegram;
import io.p1jmonitor.telegram.FluviusTelegram;
import io.p1jmonitor.telegram.Telegram;

public class FluviusTelegramConverter implements TelegramConverter {
    private final BeanTelegramConverter<FluviusTelegram> fluviusTelegramBeanTelegramConverter;

    public FluviusTelegramConverter(BeanTelegramConverterFactory beanTelegramConverterFactory) {
        this.fluviusTelegramBeanTelegramConverter = beanTelegramConverterFactory.createConverter(FluviusTelegram.class);
    }

    @Override
    public String getIdentifier() {
        return "FLU";
    }

    @Override
    public Telegram convert(COSEMTelegram cosemTelegram) {
        return fluviusTelegramBeanTelegramConverter.convert(cosemTelegram);
    }
}
