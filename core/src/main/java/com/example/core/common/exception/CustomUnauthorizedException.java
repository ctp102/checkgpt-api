package com.example.core.common.exception;

public class CustomUnauthorizedException extends CustomException {

    public CustomUnauthorizedException(int number, String message) {
        super(number, message);
    }

    public CustomUnauthorizedException(int number, String message, Throwable cause) {
        super(number, message, cause);
    }

}
