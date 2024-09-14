package com.fresh.coding.sooatelapi.exceptions;

public class HttpBadRequestException extends RuntimeException {
    public HttpBadRequestException(String message) {
        super(message);
    }
}
