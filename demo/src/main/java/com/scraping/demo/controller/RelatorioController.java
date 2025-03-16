package com.scraping.demo.controller;

import com.scraping.demo.services.BuscaProduto;
import com.scraping.demo.services.ChocolateAmericanas;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RelatorioController {
    @GetMapping("getChocolate")
    public ResponseEntity<String> getChocolate(@RequestBody String data){
        BuscaProduto chocolate = new ChocolateAmericanas();
        return ResponseEntity.ok("Ok" + chocolate.buscaProduto());
    }
}
