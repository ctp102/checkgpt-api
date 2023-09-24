package com.example.core.oauth2.enums;

import java.util.Arrays;

public enum OAuth2ProviderTypes {

    KAKAO,
    NAVER,
    GOOGLE
    ;

    public static OAuth2ProviderTypes findByName(String oAuth2Provider) {
        return Arrays.stream(OAuth2ProviderTypes.values())
                .filter(oAuth2ProviderTypes -> oAuth2ProviderTypes.name().equalsIgnoreCase(oAuth2Provider))
                .findFirst()
                .orElse(null);
    }
}
