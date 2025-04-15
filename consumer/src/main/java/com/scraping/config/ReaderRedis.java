package com.scraping.config;

import com.scraping.exceptions.DataInaccessible;
import com.scraping.exceptions.UnableRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReaderRedis {
    @Autowired
    @Qualifier("readerRedis")
    private RedisTemplate<String, Object> localCache;

    public void updateLocalCache(String key, Object value) {
        try {
            localCache.opsForValue().set(key, value);
        } catch (Exception e) {

            System.err.println("Erro updating local cache: " + e.getMessage());
            throw new UnableRedisConnection("Error while updating the local cache");
            // Conectar ao DB
        }
    }

    public Object getData(String key) {
        try{
            return localCache.opsForValue().get(key);
        } catch (Exception e) {
            throw new DataInaccessible("Error while querying local cache");
        }
    }
}
