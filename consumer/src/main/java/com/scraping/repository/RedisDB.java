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

    public void update(ProductDTO productDTO) {
        try {
            localCache.opsForValue().set(productDTO.getCode(), productDTO);
        } catch (Exception e) {
            System.err.println("Erro updating local cache: " + e.getMessage());
            throw new UnableRedisConnection("Error while updating the local cache");
            // Conectar ao DB
        }
    }

    public ProductDTO getItem(String code) {
        try{
            return localCache.opsForValue().get(code);
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

    public void clean() {
        try {
            Set<String> keys = localCache.keys("code:*"); // Padrão das chaves
            if (keys != null && !keys.isEmpty()) {
                localCache.delete(keys); // Deleta todas as chaves de uma vez
            }
        } catch (Exception e) {
            System.err.println("Erro ao limpar o cache local: " + e.getMessage());
            throw new UnableRedisConnection("Erro ao limpar o cache local");
        }
    }

}
