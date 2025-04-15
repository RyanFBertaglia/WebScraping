package com.scraping.RedisCache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

public class ProductPublisher {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void publishProductEvent(String productId, String data) {
        Map<String, String> message = new HashMap<>();
        message.put("productId", productId);
        message.put("data", data);

        redisTemplate.opsForStream().add("products:specific", message);
    }
}
