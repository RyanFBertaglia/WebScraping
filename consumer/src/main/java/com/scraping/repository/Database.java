package com.scraping.repository;

import com.scraping.entity.ProductDTO;

import java.util.ArrayList;

public interface Database {
    void update(ProductDTO productDTO);
    ProductDTO getItem(String key);
    ArrayList<ProductDTO> getAllProducts();
}
