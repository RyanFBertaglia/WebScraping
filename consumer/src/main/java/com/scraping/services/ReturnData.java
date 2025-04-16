package com.scraping.services;

import com.scraping.ProductDTO;
import com.scraping.config.ReaderRedis;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class ReturnData {

    @Autowired
    ReaderRedis readerRedis;

    public ArrayList<ProductDTO> getAllProducts() {
        return readerRedis.getAllProducts();
    }
    public ProductDTO getItem(String code) {
        return readerRedis.getItem(code);
    }
}
