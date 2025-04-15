package com.scraping.controller;

import com.scraping.services.ReturnData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("prices")
public class ReportController {
    ReturnData returnData;
    ReportController(ReturnData returnData) {
        this.returnData = returnData;
    }

    public ResponseEntity<String> pricesAllProducts() {
        String data = returnData.getAll();
        return ResponseEntity.status(200).body(data);
    }
    public ResponseEntity<String> priceSpecific(String code) {
        String data = returnData.getItem(code);
        return ResponseEntity.status(200).body(data);
    }
}
