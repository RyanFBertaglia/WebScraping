package com.scraping.config;

import com.scraping.ProductDTO;
import io.lettuce.core.RedisBusyException;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;

@Configuration
public class ReaderConfig {

    // Cria um bean compartilhado para a ConnectionFactory
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    @PostConstruct
    public void initConsumerGroup() {
        StatefulRedisConnection<String, String> connection =
                (StatefulRedisConnection<String, String>) lettuceConnectionFactory.getConnection().getNativeConnection();

        RedisCommands<String, String> sync = connection.sync();
        try {
            sync.xgroupCreate(
                    XReadArgs.StreamOffset.from("mystream", "0"), // Nome do stream e ID de início
                    "mygroup"
            );
        } catch (RedisBusyException e) {
            // Se o grupo já existir, ignoramos
            if (!e.getMessage().contains("BUSYGROUP")) {
                throw e;
            }
        }
    }

    @Bean(name = "readerRedis")
    public RedisTemplate<String, Object> readerRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }

    @Bean(name = "cacheRedis")
    public RedisTemplate<String, ProductDTO> cacheRedisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<String, ProductDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }


    @Bean
    public RedisCacheManager cacheManager(LettuceConnectionFactory factory) {
        RedisSerializationContext.SerializationPair<Object> valueSerializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(valueSerializationPair);

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }


    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, Object>> streamContainer(LettuceConnectionFactory lettuceConnectionFactory) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, Object>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                        .batchSize(10)
                        .targetType(Object.class)
                        .pollTimeout(Duration.ofSeconds(1))
                        .build();

        return StreamMessageListenerContainer.create(lettuceConnectionFactory, options);
    }
}
