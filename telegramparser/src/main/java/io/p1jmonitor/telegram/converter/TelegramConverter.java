package io.p1jmonitor.telegram.converter;

import io.p1jmonitor.telegram.COSEMTelegram;
import io.p1jmonitor.telegram.Telegram;

public interface TelegramConverter {
    String getIdentifier();
    Telegram convert(COSEMTelegram cosemTelegram);
}
