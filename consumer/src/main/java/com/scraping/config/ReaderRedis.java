package com.scraping.config;

import com.scraping.ProductDTO;
import com.scraping.exceptions.DataInaccessible;
import com.scraping.exceptions.UnableRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ReaderRedis {

    @Autowired
    @Qualifier("readerRedis")
    private RedisTemplate<String, ProductDTO> localCache;

    public void updateLocalCache(String key, ProductDTO value) {
        try {
            localCache.opsForValue().set(key, value);
        } catch (Exception e) {
            System.err.println("Erro updating local cache: " + e.getMessage());
            throw new UnableRedisConnection("Error while updating the local cache");
            // Conectar ao DB
        }
    }

    public ProductDTO getItem(String key) {
        try{
            return localCache.opsForValue().get(key);
        } catch (Exception e) {
            throw new DataInaccessible("Error while querying local cache");
        }
    }
    public ArrayList<ProductDTO> getAllProducts() {
        try {
            Set<String> keys = localCache.keys("code:*"); // busca todas as chaves de produto
            if (keys == null || keys.isEmpty()) return new ArrayList<>();

            ArrayList<ProductDTO> produtos = new ArrayList<>();
            for (String key : keys) {
                ProductDTO produto = localCache.opsForValue().get(key);
                if (produto != null) {
                    produtos.add(produto);
                }
            }
            return produtos;
        } catch (Exception e) {
            throw new DataInaccessible("Error while querying local cache");
        }
    }
}
