package io.hexbit.core.common.exception;

public class CustomAccessDeniedException extends CustomException {

    public CustomAccessDeniedException(int number, String message) {
        super(number, message);
    }

    public CustomAccessDeniedException(int number, String message, Throwable cause) {
        super(number, message, cause);
    }

}
