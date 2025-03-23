package com.scraping.controller;

import com.scraping.entities.Product;
import com.scraping.services.Pesquisa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class RelatorioController {


    private final Pesquisa pesquisa;

    @Autowired
    public RelatorioController(Pesquisa pesquisa) {
        this.pesquisa = pesquisa;
    }

    @GetMapping("/getProduct")
    public ResponseEntity<Product> getProductInformation(@RequestParam String typeProduct){
        Product product = pesquisa.findProduct(typeProduct);
        return ResponseEntity.status(200).body(product);
    }
}
