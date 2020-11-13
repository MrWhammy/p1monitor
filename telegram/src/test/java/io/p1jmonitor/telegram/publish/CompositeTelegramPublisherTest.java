package io.p1jmonitor.telegram.publish;

import io.p1jmonitor.telegram.COSEMTelegram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompositeTelegramPublisherTest {

    @Mock
    private TelegramPublisher publisher1;
    @Mock
    private TelegramPublisher publisher2;

    private CompositeTelegramPublisher compositeTelegramPublisher;

    @BeforeEach
    void setUp() {
        compositeTelegramPublisher = new CompositeTelegramPublisher(Arrays.asList(publisher1, publisher2));
    }

    @Test
    void publish() throws IOException {
        COSEMTelegram telegram = mock(COSEMTelegram.class);
        compositeTelegramPublisher.publish(telegram);
        verify(publisher1).publish(telegram);
        verify(publisher2).publish(telegram);
    }

    @Test
    void publishWithException() throws IOException {
        COSEMTelegram telegram = mock(COSEMTelegram.class);
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