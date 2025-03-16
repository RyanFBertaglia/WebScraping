package com.scraping.demo.services;

import com.scraping.demo.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Pesquisa {
    private final Map<String, BuscaProduto> buscaProdutoMap;

    public Product findProduct(String tipoProduto) {
        BuscaProduto produto = getInstanceProduct(tipoProduto);
        return produto.buscaProduto();
    }

    public BuscaProduto getInstanceProduct(String tipoProduto) {
        BuscaProduto produto = buscaProdutoMap.get(tipoProduto);
        if (produto == null) {
            throw new IllegalArgumentException("Tipo de produto inválido: " + tipoProduto);
        }
        return produto;
    }
}