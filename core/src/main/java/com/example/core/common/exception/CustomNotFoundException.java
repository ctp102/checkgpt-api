package com.example.core.common.exception;

public class CustomNotFoundException extends CustomException {

    public CustomNotFoundException(int number, String message) {
        super(number, message);
    }

    public CustomNotFoundException(int number, String message, Throwable cause) {
        super(number, message, cause);
    }

}
