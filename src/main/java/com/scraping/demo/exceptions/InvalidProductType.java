package com.scraping.demo.exceptions;

public class InvalidProductType extends RuntimeException {
    public InvalidProductType(String message) {
        super(message);
    }
    public InvalidProductType() {
        super("Invalid product type");
    }
}
