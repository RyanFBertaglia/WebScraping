package com.scraping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
