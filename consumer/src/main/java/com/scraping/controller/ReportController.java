package com.scraping.controller;

import com.scraping.ProductDTO;
import com.scraping.services.ReturnData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@RequestMapping("prices")
public class ReportController {
    ReturnData returnData;
    ReportController(ReturnData returnData) {
        this.returnData = returnData;
    }

    public ResponseEntity<ArrayList<ProductDTO>> pricesAllProducts() {
        ArrayList<ProductDTO> data = returnData.getAllProducts();
        return ResponseEntity.status(200).body(data);
    }
    public ResponseEntity<ProductDTO> priceSpecific(String code) {
        ProductDTO data = returnData.getItem(code);
        return ResponseEntity.status(200).body(data);
    }
}
