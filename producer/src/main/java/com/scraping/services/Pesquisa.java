package com.scraping.services;

import com.scraping.entities.ProductDTO;
import com.scraping.exceptions.InvalidProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Pesquisa {

    @Autowired
    private final Map<String, BuscaProduto> buscaProdutoMap;

    public ProductDTO findProduct(String tipoProduto) {
        BuscaProduto produto = getInstanceProduct(tipoProduto);
        return produto.buscaProduto();
    }

    public BuscaProduto getInstanceProduct(String tipoProduto) {
        BuscaProduto produto = buscaProdutoMap.get(tipoProduto);
        if (produto == null) {
            throw new InvalidProductType();
        }
        return produto;
    }

    public List<ProductDTO> findAllProduct(String tipoProduto) {
        List<ProductDTO> allProducts = new ArrayList<>();
        for (BuscaProduto produto : buscaProdutoMap.values()) {
            allProducts.add(produto.buscaProduto());
        }
        return allProducts;
    }
}