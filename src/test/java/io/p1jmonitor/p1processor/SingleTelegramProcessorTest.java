package io.p1jmonitor.p1processor;

import io.p1jmonitor.p1processor.publish.TelegramPublisher;
import io.p1jmonitor.p1processor.read.TelegramReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SingleTelegramProcessorTest {

    @Mock
    private TelegramPublisher telegramPublisher;
    @Mock
    private TelegramReader telegramReader;
    private SingleTelegramProcessor telegramProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        telegramProcessor = new SingleTelegramProcessor(telegramReader, telegramPublisher);
    }

    @Test
    void call() throws IOException {
        Telegram telegram = mock(Telegram.class);
        when(telegram.isChecksumValid()).thenReturn(true);
        when(telegramReader.readTelegram()).thenReturn(Optional.of(telegram));
        Boolean result = telegramProcessor.call();
        assertThat(result).isTrue();
        verify(telegramPublisher).publish(telegram);
    }

    @Test
    void callEOF() throws IOException {
        when(telegramReader.readTelegram()).thenReturn(Optional.empty());
        Boolean result = telegramProcessor.call();
        assertThat(result).isFalse();
        verify(telegramPublisher, never()).publish(any(Telegram.class));
    }

    @Test
    void callInvalidChecksum() throws IOException {
        Telegram telegram = mock(Telegram.class);
        when(telegram.isChecksumValid()).thenReturn(false);
        when(telegramReader.readTelegram()).thenReturn(Optional.of(telegram));
        Boolean result = telegramProcessor.call();
        assertThat(result).isFalse();
        verify(telegramPublisher, never()).publish(any(Telegram.class));
    }
}