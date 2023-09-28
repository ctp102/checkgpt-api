package io.hexbit.core.common.enums;

import io.hexbit.core.common.response.CustomResponseCodes;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorCodes implements CustomResponseCodes {

    NOT_MATCH_OAUTH2_PROVIDER               (400, "일치하는 OAuth2 Provider가 없습니다."),

    NOT_EXISTS_USER                         (404, "존재하지 않는 사용자입니다."),

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
