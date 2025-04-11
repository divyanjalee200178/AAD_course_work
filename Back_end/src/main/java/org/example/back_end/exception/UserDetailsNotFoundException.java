package org.example.back_end.exception;

public class UserDetailsNotFoundException extends RuntimeException {
    public UserDetailsNotFoundException(String message) {
        super(message);
    }
}
