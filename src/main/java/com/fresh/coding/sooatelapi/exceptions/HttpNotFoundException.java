package com.fresh.coding.sooatelapi.exceptions;

public class HttpNotFoundException extends RuntimeException {
    public HttpNotFoundException(String message) {
        super(message);
    }
}
