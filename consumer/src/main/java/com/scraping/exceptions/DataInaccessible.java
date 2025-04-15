package com.scraping.exceptions;

public class DataInaccessible extends RuntimeException {
    public DataInaccessible(String message) {
        super(message);
    }
}
