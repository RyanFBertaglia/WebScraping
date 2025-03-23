package com.scraping.exceptions;

public class NotFoundItem extends RuntimeException {
    public NotFoundItem() {
        super("Error while founding the item");
    }
}
