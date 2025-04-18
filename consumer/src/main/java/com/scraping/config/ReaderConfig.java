package com.scraping.config;

import com.scraping.ProductDTO;
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
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;
import java.util.Map;

@Configuration
public class ReaderConfig {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final LettuceConnectionFactory lettuceConnectionFactoryLocal;
    private final LettuceConnectionFactory lettuceConnectionFactoryStream;

    public ReaderConfig(
            @Qualifier("lettuceConnectionFactoryLocal") LettuceConnectionFactory lettuceConnectionFactoryLocal,
            @Qualifier("lettuceConnectionFactoryStream") LettuceConnectionFactory lettuceConnectionFactoryStream) {
        this.lettuceConnectionFactoryLocal = lettuceConnectionFactoryLocal;
        this.lettuceConnectionFactoryStream = lettuceConnectionFactoryStream;
    }



    @PostConstruct
    public void initConsumerGroup() {
        String streamKey = "consumer-1";
        String groupName = "product-consumer-group";

        // Garante que o stream exista antes de criar o grupo
        if (!Boolean.TRUE.equals(stringRedisTemplate.hasKey(streamKey))) {
            stringRedisTemplate.opsForStream().add(streamKey, Map.of("init", "1"));
        }

        // Tenta criar o grupo
        try {
            stringRedisTemplate.opsForStream()
                    .createGroup(streamKey, ReadOffset.from("0"), groupName);
            System.out.println("Grupo criado com sucesso.");
        } catch (Exception e) {
            if (e.getMessage().contains("BUSYGROUP")) {
                System.out.println("Grupo já existe.");
            } else {
                throw new RuntimeException("Cannot create reader group");
            }
        }
    }

    @Bean(name = "readerRedis")
    public RedisTemplate<String, ProductDTO> readerRedisTemplate() {
        RedisTemplate<String, ProductDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactoryStream);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(ProductDTO.class));
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
                        .pollTimeout(Duration.ofSeconds(1))
                        .build();

        return StreamMessageListenerContainer.create(lettuceConnectionFactoryStream, options);
    }
}
