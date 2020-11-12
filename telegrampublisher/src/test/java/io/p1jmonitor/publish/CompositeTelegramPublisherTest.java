package io.p1jmonitor.publish;

import io.p1jmonitor.RawTelegram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CompositeTelegramPublisherTest {
    @Mock
    private TelegramPublisher publisher1;
    @Mock
    private TelegramPublisher publisher2;

    private CompositeTelegramPublisher compositeTelegramPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        compositeTelegramPublisher = new CompositeTelegramPublisher(Arrays.asList(publisher1, publisher2));
    }

    @Test
    void publish() throws IOException {
        RawTelegram telegram = mock(RawTelegram.class);
        compositeTelegramPublisher.publish(telegram);
        verify(publisher1).publish(telegram);
        verify(publisher2).publish(telegram);
    }

    @Test
    void publishWithException() throws IOException {
        RawTelegram telegram = mock(RawTelegram.class);
        doThrow(IOException.class).when(publisher1).publish(telegram);
        assertThrows(IOException.class, () -> compositeTelegramPublisher.publish(telegram));
        verify(publisher2, never()).publish(telegram);
    }

    @Test
    void close() throws Exception {
        compositeTelegramPublisher.close();
        verify(publisher1).close();
        verify(publisher2).close();
    }

    @Test
    void closeWithException() throws Exception {
        doThrow(IOException.class).when(publisher1).close();
        assertThrows(IOException.class, () -> compositeTelegramPublisher.close());
        verify(publisher2).close();
    }
}