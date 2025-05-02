package com.scraping.exceptions;

public class DataInaccessible extends RuntimeException {
    // Construtor que aceita apenas uma String
    public DataInaccessible(String message) {
        super(message);
    }

    // Construtor que aceita uma String e uma Exception
    public DataInaccessible(String message, Throwable cause) {
        super(message, cause); // Passa a mensagem e a causa para a superclasse
    }
}

