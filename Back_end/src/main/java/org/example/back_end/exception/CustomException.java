package org.example.back_end.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus statusCode;

    public CustomException(String message, int statusCode) {
        super(message);
        this.statusCode = HttpStatus.valueOf(statusCode);
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
