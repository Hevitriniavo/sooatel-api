package com.fresh.coding.sooatelapi.exceptions;

public class HttpInternalServerException extends RuntimeException {
    public HttpInternalServerException(String message) {
        super(message);
    }
}
