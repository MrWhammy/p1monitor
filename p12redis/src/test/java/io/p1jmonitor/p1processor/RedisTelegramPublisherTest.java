package io.p1jmonitor.p1processor;

import io.p1jmonitor.telegram.COSEMTelegram;
import io.p1jmonitor.telegram.Telegram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisTelegramPublisherTest {

    private static final String TOPIC_NAME = "TOPIC";

    @Mock
    private Jedis jedis;

    private RedisTelegramPublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new RedisTelegramPublisher(jedis, TOPIC_NAME);
    }

    @Test
    void create() throws TelegramException {
        COSEMTelegram telegram = mock(COSEMTelegram.class);
        when(telegram.toString()).thenReturn("MESSAGE");

        publisher.publish(telegram);

        verify(jedis).publish(TOPIC_NAME, "MESSAGE");
    }

    @Test
    void close() throws TelegramException {
        publisher.close();

        verify(jedis).close();
    }
}