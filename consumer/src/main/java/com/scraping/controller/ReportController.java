package com.scraping.controller;

import com.scraping.entity.ProductDTO;
import com.scraping.repository.RedisDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/prices")
public class ReportController {

    private final RedisDB redisDB;

    @Autowired
    ReportController(RedisDB redisDB) {
        this.redisDB = redisDB;
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<ArrayList<ProductDTO>> pricesAllProducts() {
        ArrayList<ProductDTO> data = redisDB.getAllProducts();
        return ResponseEntity.status(200).body(data);
    }

    @GetMapping("/getSpecific/{code}")
    public ResponseEntity<ProductDTO> priceSpecific(@PathVariable String code) {
        ProductDTO data = redisDB.getItem(code);
        return ResponseEntity.status(200).body(data);
    }
}
