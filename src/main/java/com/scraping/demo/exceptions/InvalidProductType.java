package com.scraping.demo.exceptions;

public class InvalidProductType extends RuntimeException {
    public InvalidProductType() {
        super("Invalid product type");
    }
}
