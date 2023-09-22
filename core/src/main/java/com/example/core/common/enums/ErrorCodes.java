package com.example.core.common.enums;

import com.example.core.common.response.CustomResponseCodes;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorCodes implements CustomResponseCodes {

    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_SERVER_ERROR                   (500, "예상치 못한 에러가 발생했습니다.")
    ;

    private final int number;
    private final String message;

    ErrorCodes(int number, String message) {
        this.number = number;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    public static ErrorCodes findByMessage(String message) {
        return Arrays.stream(ErrorCodes.values())
                .filter(e -> e.getMessage().equals(message))
                .findFirst()
                .orElse(null);
    }

}
