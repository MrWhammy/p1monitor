package io.p1jmonitor.p1processor;

import io.p1jmonitor.telegram.COSEMTelegram;
import io.p1jmonitor.telegram.io.ReadTelegram;
import io.p1jmonitor.telegram.publish.TelegramPublisher;
import io.p1jmonitor.telegram.io.TelegramReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SingleTelegramProcessorTest {

    @Mock
    private TelegramPublisher telegramPublisher;
    @Mock
    private TelegramReader telegramReader;
    private SingleTelegramProcessor telegramProcessor;

    @BeforeEach
    void setUp() {
        telegramProcessor = new SingleTelegramProcessor(telegramReader, telegramPublisher);
    }

    @Test
    void call() throws IOException {
        ReadTelegram telegram = mock(ReadTelegram.class);
        COSEMTelegram cosemTelegram = mock(COSEMTelegram.class);
        when(telegram.getTelegram()).thenReturn(cosemTelegram);
        when(telegram.isChecksumValid()).thenReturn(true);
        when(telegramReader.readTelegram()).thenReturn(Optional.of(telegram));
        Boolean result = telegramProcessor.call();
        assertThat(result).isTrue();
        verify(telegramPublisher).publish(cosemTelegram);
    }

    @Test
    void callEOF() throws IOException {
        when(telegramReader.readTelegram()).thenReturn(Optional.empty());
        Boolean result = telegramProcessor.call();
        assertThat(result).isFalse();
        verify(telegramPublisher, never()).publish(any(COSEMTelegram.class));
    }

    @Test
    void callInvalidChecksum() throws IOException {
        ReadTelegram telegram = mock(ReadTelegram.class);
        when(telegram.isChecksumValid()).thenReturn(false);
        when(telegramReader.readTelegram()).thenReturn(Optional.of(telegram));
        Boolean result = telegramProcessor.call();
        assertThat(result).isFalse();
        verify(telegramPublisher, never()).publish(any(COSEMTelegram.class));
    }
}