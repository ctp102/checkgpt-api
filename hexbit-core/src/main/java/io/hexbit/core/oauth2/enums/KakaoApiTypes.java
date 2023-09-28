package io.hexbit.core.oauth2.enums;

import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public enum KakaoApiTypes {

    GET_AUTHORIZATION_CODE  ("https://kauth.kakao.com/oauth/authorize", HttpMethod.GET,  "인가 코드 받기"),
    GET_TOKEN               ("https://kauth.kakao.com/oauth/token",     HttpMethod.GET,  "토큰 받기"),
    UPDATE_TOKENS           ("https://kauth.kakao.com/oauth/token",     HttpMethod.POST, "토큰 갱신"),
    LOGOUT_WITH_ACCOUNT     ("https://kauth.kakao.com/oauth/logout",    HttpMethod.GET,  "카카오 계정과 함께 로그아웃"),

    GET_ACCESS_TOKEN_INFO   ("https://kapi.kakao.com/v1/user/access_token_info",    HttpMethod.GET,  "액세스 토큰 정보 조회"),
    GET_USER_INFO           ("https://kapi.kakao.com/v2/user/me",                   HttpMethod.GET,  "사용자 정보 조회"),
    LOGOUT                  ("https://kapi.kakao.com/v1/user/logout",               HttpMethod.POST, "로그아웃"),
    UNLINK                  ("https://kapi.kakao.com/v1/user/unlink",               HttpMethod.POST, "연결 끊기"),
    GET_AGREEMENT_LIST      ("https://kapi.kakao.com/v2/user/scopes",               HttpMethod.GET,  "동의 내역 확인하기"),
    ;

    private final String endPoint;
    private final HttpMethod methodType;
    private final String description;

    KakaoApiTypes(String endPoint, HttpMethod methodType, String description) {
        this.endPoint = endPoint;
        this.methodType = methodType;
        this.description = description;
    }

}
