package io.hexbit.core.common.exception;

public class CustomConflictException extends CustomException {

    public CustomConflictException(int number, String message) {
        super(number, message);
    }

    public CustomConflictException(int number, String message, Throwable cause) {
        super(number, message, cause);
    }

}
