package com.scraping.RedisCache;


import com.scraping.entities.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductPublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean publishProductEvent(ProductDTO data) {

        Map<String, String> fields = new HashMap<>();
        fields.put("code", data.getCode());
        fields.put("name", data.getName());
        fields.put("price", data.getPrice());
        fields.put("local", data.getLocal());

        // Escrevendo no stream 'products:specific'
        redisTemplate.opsForStream().add("products:specific", fields);
        return true;
    }
}
