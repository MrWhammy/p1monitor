package io.p1jmonitor.p1processor;

import io.p1jmonitor.telegram.COSEMTelegram;
import io.p1jmonitor.telegram.raw.publish.TelegramPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.net.URI;

import static io.p1jmonitor.p1processor.TelegramException.Error.REDIS_ERROR;

public class RedisTelegramPublisher implements TelegramPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTelegramPublisher.class);

    public static RedisTelegramPublisher create(URI redisUri, String topicName) throws TelegramException {
        try {
            Jedis jedis;
            if (redisUri != null) {
                LOGGER.debug("Connecting to Redis {}", redisUri);
                jedis = new Jedis(redisUri);
            } else {
                LOGGER.debug("Connecting to Redis using default parameters");
                jedis = new Jedis();
            }
            jedis.connect();
            return new RedisTelegramPublisher(jedis, topicName);
        } catch (JedisConnectionException e) {
            LOGGER.error("Problem connecting to Redis {}", redisUri, e);
            throw new TelegramException(REDIS_ERROR, "Problem connecting to Redis");
        }
    }

    private final Jedis jedis;
    private final String topicName;

    public RedisTelegramPublisher(Jedis jedis, String topicName) {
        this.jedis = jedis;
        this.topicName = topicName;
    }

    @Override
    public void publish(COSEMTelegram telegram) throws TelegramException {
        LOGGER.debug("Publishing telegram to {}", topicName);
        try {
            long receivers = jedis.publish(topicName, telegram.toString());
            LOGGER.info("Published telegram to {} receivers", receivers);
        } catch (JedisException e) {
            LOGGER.error("Problem publishing to {}", topicName, e);
            throw new TelegramException(REDIS_ERROR, "Problem publishing to " + topicName);
        }
    }

    @Override
    public void close() throws TelegramException {
        try {
            jedis.close();
        } catch (JedisException e) {
            LOGGER.error("Problem closing Redis client", e);
            throw new TelegramException(REDIS_ERROR, "Problem closing Redis client");
        }
    }
}
