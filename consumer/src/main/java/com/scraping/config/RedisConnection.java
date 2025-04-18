package com.scraping.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConnection {
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactoryLocal() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    //Redis Stream
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactoryStream() {
        return new LettuceConnectionFactory("localhost", 6380);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(@Qualifier("lettuceConnectionFactoryStream") LettuceConnectionFactory lettuceConnectionFactoryStream) {
        return new StringRedisTemplate(lettuceConnectionFactoryStream);
    }
}
