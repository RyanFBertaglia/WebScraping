package com.scraping.repository;

import com.scraping.entity.ProductDTO;
import com.scraping.exceptions.DataInaccessible;
import com.scraping.exceptions.UnableRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class RedisDB implements Database{

    @Autowired
    @Qualifier("cacheRedis")
    private RedisTemplate<String, ProductDTO> localCache;

    @Autowired
    private MongoDB mongoDB;

    public void update(ProductDTO productDTO) {
        try {
            localCache.opsForValue().set(productDTO.getCode(), productDTO);
        } catch (Exception e) {
            System.err.println("Erro updating local cache: " + e.getMessage());
            throw new UnableRedisConnection("Error while updating the local cache");
        }
    }

    public ProductDTO getItem(String code) {
        try{
            return localCache.opsForValue().get(code);
        } catch (Exception errorLocal) {
            try{
                return mongoDB.getItem(code);
            } catch(Exception errorMongoDB) {
                throw new DataInaccessible("Error while querying the database");
            }
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
            try {
                return mongoDB.getAllProducts();
            } catch (Exception errorReturningAllProducts) {
                throw new DataInaccessible("Error while querying local cache ");
            }
        }
    }

    public void clean() {
        try {
            Set<String> keys = localCache.keys("code:*");
            if (keys != null && !keys.isEmpty()) {
                localCache.delete(keys);
            }
        } catch (Exception e) {
            System.err.println("Error while cleaning the local cache: " + e.getMessage());
            throw new UnableRedisConnection("Error while cleaning the cache");
        }
    }
}
