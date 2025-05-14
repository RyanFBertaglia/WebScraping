package com.scraping.exceptions;

public class NotSentProduct extends RuntimeException {
    public NotSentProduct() {
        super("Error while sending the product");
    }
}
