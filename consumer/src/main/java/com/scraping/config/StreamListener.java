package com.scraping.config;

import com.scraping.entity.ProductDTO;
import com.scraping.repository.RedisDB;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public class StreamListener {

    private StreamMessageListenerContainer<String, ObjectRecord<String, ProductDTO>> listenerContainer;
    private final RedisDB redisDB;

    @Autowired
    StreamListener(StreamMessageListenerContainer<String, ObjectRecord<String, ProductDTO>> listenerContainer,
                   RedisDB redisDB) {
        this.listenerContainer = listenerContainer;
        this.redisDB = redisDB;
    }




    @PostConstruct
    public void startListener() {
        listenerContainer.receiveAutoAck(
                Consumer.from("product-consumer-group", "consumer-1"),
                StreamOffset.create("products:specific", ReadOffset.lastConsumed()),
                record -> {
                    ProductDTO product = record.getValue();
                    redisDB.update(product);
                    System.out.println("Produto recived and cache updated: " + product.getCode());
                }
        );
    }
}
