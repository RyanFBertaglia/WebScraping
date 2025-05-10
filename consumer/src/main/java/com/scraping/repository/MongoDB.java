package com.scraping.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.UpdateOptions;
import com.scraping.config.MongoDBConnection;
import com.scraping.entity.ProductDTO;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

@Service
public class MongoDB implements Database{

    MongoDBConnection mongoDBConnection;

    @Autowired
    public MongoDB(MongoDBConnection mongoDBConnection) {
        this.mongoDBConnection = mongoDBConnection;
    }

    public void update(ProductDTO productDTO) {
        MongoCollection<Document> collection = mongoDBConnection.getProducts();

        Bson filter = eq("code", productDTO.getCode());

        Bson update = Updates.combine(
                Updates.set("name", productDTO.getName()),
                Updates.set("price", productDTO.getPrice()),
                Updates.set("local", productDTO.getLocal())
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        collection.updateOne(filter, update, options);
    }

    public ProductDTO getItem(String code) {
        MongoCollection<Document> collection = mongoDBConnection.getProducts();
        Document doc = collection.find(eq("code", code)).first();
        return ProductDTO.fromDocument(doc);
    }

    public ArrayList<ProductDTO> getAllProducts() {
        MongoCollection<Document> collection = mongoDBConnection.getProducts();
        ArrayList<ProductDTO> products = new ArrayList<>();

        for (Document doc : collection.find()) {
            products.add(ProductDTO.fromDocument(doc));
        }
        return products;
    }
}