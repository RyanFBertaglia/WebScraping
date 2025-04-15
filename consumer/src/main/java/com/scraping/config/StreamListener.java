package com.scraping.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

public class StreamListener {
    @Autowired
    private StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer;

    @Autowired
    private ReaderRedis readerRedis;

    @PostConstruct
    public void startListener() {
        listenerContainer.receiveAutoAck(
                Consumer.from("product-consumer-group", "consumer-1"),
                StreamOffset.create("products:specific", ReadOffset.lastConsumed()),
                message -> {
                    String productId = message.getValue().get("productId");
                    String data = message.getValue().get("data");

                    readerRedis.updateLocalCache(productId, data);

                    System.out.println("Produto recebido e cache atualizado: " + productId);

                }
        );
        listenerContainer.start();
    }
}
