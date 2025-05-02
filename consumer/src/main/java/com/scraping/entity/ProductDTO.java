package com.scraping.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    String code;
    String name;
    String price;
    String local;

    ProductDTO(String name, String price, String local) {
        this.name = name;
        this.price = price;
        this.local = local;
    }

    public static ProductDTO fromDocument(Document doc) {
        if (doc == null) return null;

        return new ProductDTO(
                doc.getString("code"),
                doc.getString("name"),
                doc.getString("price"),
                doc.getString("local")
        );
    }
}
