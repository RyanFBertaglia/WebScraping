package com.scraping.config;

import com.scraping.ProductDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public class StreamListener {
    @Autowired
    private StreamMessageListenerContainer<String, ObjectRecord<String, ProductDTO>> listenerContainer;

    @Autowired
    private ReaderRedis readerRedis;


    @PostConstruct
    public void startListener() {
        listenerContainer.receiveAutoAck(
                Consumer.from("product-consumer-group", "consumer-1"),
                StreamOffset.create("products:specific", ReadOffset.lastConsumed()),
                record -> {
                    ProductDTO product = record.getValue();
                    String code = product.getCode();
                    readerRedis.updateLocalCache(code, product);
                    System.out.println("Produto recived and cache updated: " + code);
                }
        );
        listenerContainer.start();
    }
}
