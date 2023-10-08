package io.hexbit.core.oauth2.enums;

import io.hexbit.core.common.enums.ErrorCodes;
import io.hexbit.core.common.exception.CustomBadRequestException;

import java.util.Arrays;

public enum OAuth2ProviderTypes {

    KAKAO,
    NAVER,
    GOOGLE
    ;

    public static void checkRegisteredOAuth2Provider(String oAuth2Provider) {
        if (findByName(oAuth2Provider) == null) {
            throw new CustomBadRequestException(ErrorCodes.NOT_REGISTERED_OAUTH2_PROVIDER.getNumber(), ErrorCodes.NOT_REGISTERED_OAUTH2_PROVIDER.getMessage());
        }
    }

    public static OAuth2ProviderTypes findByName(String oAuth2Provider) {
        return Arrays.stream(OAuth2ProviderTypes.values())
                .filter(oAuth2ProviderTypes -> oAuth2ProviderTypes.name().equalsIgnoreCase(oAuth2Provider))
                .findFirst()
                .orElse(null);
    }
}
