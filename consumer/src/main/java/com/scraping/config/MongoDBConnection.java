package com.scraping.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MongoDBConnection {

    private final String CONNECTION_STRING;
    private final String DATABASE;

    public MongoDBConnection(
            @Value("${spring.mongodb.url}") String connectionString,
            @Value("${spring.mongodb.database}") String databaseName) {
        this.CONNECTION_STRING = connectionString;
        this.DATABASE = databaseName;
    }


    public MongoClient getMongoClient() {
        return MongoClients.create(CONNECTION_STRING);
    }

    public MongoDatabase getDatabase() {
        return getMongoClient().getDatabase(DATABASE);
    }
}