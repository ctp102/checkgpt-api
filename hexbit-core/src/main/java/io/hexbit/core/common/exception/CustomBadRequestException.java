package io.hexbit.core.common.exception;

public class CustomBadRequestException extends CustomException {

    public CustomBadRequestException(int number, String message) {
        super(number, message);
    }

    public CustomBadRequestException(int number, String message, Throwable cause) {
        super(number, message, cause);
    }

}
