package com.scraping.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MongoDBConnection {

    @Value("${spring.mongodb.url}")
    private static String CONNECTION_STRING;

    public static MongoClient getMongoClient() {
        return MongoClients.create(CONNECTION_STRING);
    }

    public MongoDatabase getDatabase() {
        return getMongoClient().getDatabase("sample_mflix");
    }
}