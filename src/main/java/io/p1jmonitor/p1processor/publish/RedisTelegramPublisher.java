package io.p1jmonitor.p1processor.publish;

import io.p1jmonitor.p1processor.Telegram;
import io.p1jmonitor.p1processor.TelegramException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class RedisTelegramPublisher implements TelegramPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTelegramPublisher.class);

    public static RedisTelegramPublisher create(String topicName) {
        // TODO: config
        Jedis jedis = new Jedis();
        return new RedisTelegramPublisher(jedis, topicName);
    }

    private final Jedis jedis;
    private final String topicName;

    public RedisTelegramPublisher(Jedis jedis, String topicName) {
        this.jedis = jedis;
        this.topicName = topicName;
    }

    @Override
    public void publish(Telegram telegram) {
        LOGGER.debug("Publishing telegram to {}", topicName);
        try {
            long receivers = jedis.publish(topicName, telegram.getTelegram());
            LOGGER.info("Published telegram to {} receivers", receivers);
        } catch (JedisException e) {
            LOGGER.error("Problem publishing to {}", topicName, e);
            throw new TelegramException(TelegramException.Error.REDIS_ERROR, "Problem publishing to " + topicName);
        }
    }

    @Override
    public void close() {
        try {
            jedis.shutdown();
        } catch (JedisException e) {
            LOGGER.error("Problem closing Redis client", e);
            throw new TelegramException(TelegramException.Error.REDIS_ERROR, "Problem closing Redis client");
        }
    }
}
