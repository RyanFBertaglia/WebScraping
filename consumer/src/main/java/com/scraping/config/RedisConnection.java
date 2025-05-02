package com.scraping.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConnection {

    @Value("${spring.redis.cache.host}")
    private String localRedisHost;

    @Value("${spring.redis.cache.port}")
    private int localRedisPort;

    @Value("${spring.redis.stream.port}")
    private int redisStreamPort;

    @Value("${spring.redis.stream.host}")
    private String redisStreamHost;


    @Bean(name = "lettuceConnectionFactoryLocal")
    public LettuceConnectionFactory lettuceConnectionFactoryLocal() {
        return new LettuceConnectionFactory(localRedisHost, localRedisPort);
    }

    @Bean(name = "lettuceConnectionFactoryStream")
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactoryStream() {
        return new LettuceConnectionFactory(redisStreamHost, redisStreamPort);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(@Qualifier("lettuceConnectionFactoryStream") LettuceConnectionFactory lettuceConnectionFactoryStream) {
        return new StringRedisTemplate(lettuceConnectionFactoryStream);
    }
}
