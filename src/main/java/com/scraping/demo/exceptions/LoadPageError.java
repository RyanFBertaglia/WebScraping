package com.scraping.demo.exceptions;

public class LoadPageError extends RuntimeException {
    public LoadPageError(String message) {
        super(message);
    }
    public LoadPageError() {
        super("Error loading page");
    }
}
