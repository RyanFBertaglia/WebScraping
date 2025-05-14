package com.scraping.services;

import com.scraping.exceptions.NotSentProduct;
import com.scraping.repository.ProductPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveProduct {

    ProductPublisher productPublisher;
    ChocolateAtacadao chocolateAtacadao;
    ChocolateSpani chocolateSpani;
    ChocolateAmericanas chocolateAmericanas;

    @Autowired
    public SaveProduct(ProductPublisher productPublisher, ChocolateAmericanas chocolateAmericanas,
                       ChocolateSpani chocolateSpani, ChocolateAtacadao chocolateAtacadao) {
        this.productPublisher = productPublisher;
        this.chocolateAmericanas = chocolateAmericanas;
        this.chocolateSpani = chocolateSpani;
        this.chocolateAtacadao = chocolateAtacadao;
    }

    public void executeScraping() {
        try {
            productPublisher.publishProductEvent(chocolateAtacadao.buscaProduto());
            productPublisher.publishProductEvent(chocolateSpani.buscaProduto());
            productPublisher.publishProductEvent(chocolateAmericanas.buscaProduto());
        } catch (Exception e) {
            throw new NotSentProduct();
        }
    }
}
