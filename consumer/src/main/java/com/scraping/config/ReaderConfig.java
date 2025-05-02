package com.scraping.config;

import com.scraping.entity.ProductDTO;
import com.scraping.exceptions.DataInaccessible;
import io.lettuce.core.RedisBusyException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;

@Configuration
public class ReaderConfig {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("lettuceConnectionFactoryLocal")
    private LettuceConnectionFactory lettuceConnectionFactoryLocal;

    @Autowired
    @Qualifier("lettuceConnectionFactoryStream")
    private LettuceConnectionFactory lettuceConnectionFactoryStream;


    @PostConstruct
    public void initConsumerGroup() {
        String streamKey = "products:specific";
        String groupName = "product-consumer-group";

        try {
            stringRedisTemplate.opsForStream()
                    .createGroup(streamKey, ReadOffset.from("0"), groupName);
            System.out.println("Reader group created.");
        } catch (Exception e) {
            Throwable cause = e.getCause();

            // Verifica se a causa direta ou indireta é um RedisBusyException
            while (cause != null) {
                if (cause instanceof RedisBusyException) {
                    System.out.println("Group already exist. Continuing the process.");
                    return;
                }
                cause = cause.getCause();
            }
            throw new DataInaccessible("Cannot create reader group", e);
        }

        //Todo: Arrumar o cache local

    }

    @Bean(name = "readerRedisTemplate")
    public RedisTemplate<String, ProductDTO> readerRedisTemplate() {
        RedisTemplate<String, ProductDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactoryStream);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name = "cacheRedis")
    public RedisTemplate<String, ProductDTO> cacheRedisTemplate() {
        RedisTemplate<String, ProductDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactoryLocal);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, ProductDTO>> streamContainer() {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, ProductDTO>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                        .batchSize(10)
                        .targetType(ProductDTO.class)
                        .pollTimeout(Duration.ofSeconds(15))
                        .build();

        return StreamMessageListenerContainer.create(lettuceConnectionFactoryStream, options);
    }
}
