package io.p1jmonitor.p1processor;

import io.p1jmonitor.p1processor.read.ByteTelegramReader;
import io.p1jmonitor.p1processor.read.InputStreamByteReader;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.InstanceOfAssertFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ByteTelegramReaderTest {

    @Test
    void readTelegram() {
        try (ByteTelegramReader byteTelegramReader = new ByteTelegramReader(new InputStreamByteReader(ByteTelegramReaderTest.class.getResourceAsStream("/sample.txt")))) {
            Optional<Telegram> optionalTelegram = byteTelegramReader.readTelegram();
            assertThat(optionalTelegram).isPresent();
            Telegram telegram = optionalTelegram.get();
            assertThat(telegram.getTelegram()).startsWith("/FLU5\\253769484_A\r\n").endsWith("\r\n!A72D\r\n");
            assertThat(telegram.getChecksumValue()).isEqualTo(0xA72D);
            // FIXME: fix checksum and test validation
        }
    }

    @Test
    void readEmptyTelegram() {
        try (ByteTelegramReader byteTelegramReader = new ByteTelegramReader(new InputStreamByteReader(new ByteArrayInputStream(new byte[0])))) {
            Optional<Telegram> optionalTelegram = byteTelegramReader.readTelegram();
            assertThat(optionalTelegram).isEmpty();
        }
    }

    @Test
    void readHalfTelegram() {
        try (ByteTelegramReader byteTelegramReader = new ByteTelegramReader(new InputStreamByteReader(ByteTelegramReaderTest.class.getResourceAsStream("/eof.txt")))) {
            Optional<Telegram> optionalTelegram = byteTelegramReader.readTelegram();
            assertThat(optionalTelegram).isEmpty();
        }
    }
}