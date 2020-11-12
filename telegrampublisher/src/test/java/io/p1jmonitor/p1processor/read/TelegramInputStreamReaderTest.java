package io.p1jmonitor.p1processor.read;

import io.p1jmonitor.p1processor.Telegram;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TelegramInputStreamReaderTest {

    @Test
    void readTelegram() throws IOException {
        try (TelegramInputStreamReader byteTelegramReader = new TelegramInputStreamReader(TelegramInputStreamReaderTest.class.getResourceAsStream("/sample.txt"))) {
            Optional<Telegram> optionalTelegram = byteTelegramReader.readTelegram();
            assertThat(optionalTelegram).isPresent();
            Telegram telegram = optionalTelegram.get();
            assertThat(telegram.getTelegram()).startsWith("/FLU5\\253769484_A\r\n").endsWith("\r\n!A72D\r\n");
            assertThat(telegram.getChecksumValue()).isEqualTo(0xA72D);
            assertThat(telegram.getCalculatedChecksum().getValue()).isEqualTo(0xA72D);
            assertThat(telegram.isChecksumValid()).isTrue();
        }
    }

    @Test
    void readEmptyTelegram() throws IOException {
        try (TelegramInputStreamReader byteTelegramReader = new TelegramInputStreamReader(new ByteArrayInputStream(new byte[0]))) {
            Optional<Telegram> optionalTelegram = byteTelegramReader.readTelegram();
            assertThat(optionalTelegram).isEmpty();
        }
    }

    @Test
    void readHalfTelegram() throws IOException {
        try (TelegramInputStreamReader byteTelegramReader = new TelegramInputStreamReader(TelegramInputStreamReaderTest.class.getResourceAsStream("/eof.txt"))) {
            Optional<Telegram> optionalTelegram = byteTelegramReader.readTelegram();
            assertThat(optionalTelegram).isEmpty();
        }
    }
}