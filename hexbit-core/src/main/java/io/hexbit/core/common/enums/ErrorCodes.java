package io.hexbit.core.common.enums;

import io.hexbit.core.common.response.CustomResponseCodes;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorCodes implements CustomResponseCodes {

    NOT_REGISTERED_OAUTH2_PROVIDER          (400, "등록되지 않은 OAuth2 Provider"),
    JWT_TOKEN_EXPIRED                       (400, "JWT 토큰 만료"),
    JWT_TOKEN_NOT_SUPPORTED                 (400, "지원하지 않는 JWT 토큰"),
    JWT_TOKEN_MALFORMED                     (400, "조작된 JWT 토큰"),
    JWT_TOKEN_SIGNATURE_ERROR               (400, "JWT 토큰 서명 오류"),
    JWT_TOKEN_ILLEGAL_ARGUMENT              (400, "JWT 토큰 잘못된 인자"),
    JWT_TOKEN_EXCEPTION                     (400, "JWT 토큰 예외 발생"),
    JWT_TOKEN_DECRYPT_FAILED                (400, "JWT 토큰 복호화 실패"),
    EMPTY_ACCESS_TOKEN                      (400, "AccessToken 누락"),

    SESSION_USER_NOT_MATCHED                (401, "세션 사용자 불일치"),

    NOT_EXISTS_USER                         (404, "존재하지 않는 사용자"),

    INTERNAL_SERVER_ERROR                   (500, "예상치 못한 에러가 발생")
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
