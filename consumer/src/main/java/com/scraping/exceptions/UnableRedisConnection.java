package com.scraping.exceptions;

public class UnableRedisConnection extends RuntimeException {
    public UnableRedisConnection(String message) {
        super(message);
    }
    public UnableRedisConnection() {
        super("Fail to connect to your redis");
    }
}
