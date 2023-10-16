package io.hexbit.core.oauth2.service;

import io.hexbit.core.common.enums.ErrorCodes;
import io.hexbit.core.common.exception.CustomException;
import io.hexbit.core.oauth2.domain.KakaoOAuth2Token;
import io.hexbit.core.oauth2.domain.KakaoOAuth2User;
import io.hexbit.core.oauth2.domain.KakaoResponse;
import io.hexbit.core.oauth2.dto.OAuth2RequestDto;
import io.hexbit.core.oauth2.enums.KakaoLoginApiTypes;
import io.hexbit.core.oauth2.enums.OAuth2ProviderTypes;
import io.hexbit.core.oauth2.form.KakaoOAuth2TokenForm;
import io.hexbit.core.oauth2.restclient.KakaoRestClient;
import io.hexbit.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuth2Service implements OAuth2Service {

    private final KakaoRestClient kakaoRestClient;

    @Override
    public String getOAuth2AuthorizationURI(String redirectURI) {

        return UriComponentsBuilder.fromUriString(KakaoLoginApiTypes.GET_AUTHORIZATION_CODE.getEndPoint())
                .queryParam("client_id", kakaoRestClient.getKakaoOAuth2Properties().getClientId())
                .queryParam("redirect_uri", redirectURI)
                .queryParam("response_type", "code")
                .queryParam("scope", "profile_nickname, profile_image, account_email")
                .toUriString();
    }

    @Override
    public User getOAuth2User(OAuth2RequestDto oAuth2RequestDto) {

        KakaoOAuth2User kakaoOAuth2UserResponseData = getKakaoOAuth2User(oAuth2RequestDto.getAccessToken());

        return User.of(
                kakaoOAuth2UserResponseData.getId(),
                OAuth2ProviderTypes.KAKAO.name().toLowerCase(),
                kakaoOAuth2UserResponseData.getKakaoAccount().getEmail(),
                kakaoOAuth2UserResponseData.getKakaoAccount().getProfile().getNickname()
        );
    }

    public KakaoOAuth2Token getOAuth2Token(KakaoOAuth2TokenForm kakaoOAuth2TokenForm) {

        KakaoResponse<KakaoOAuth2Token> oAuth2TokenResponse = kakaoRestClient.getOAuth2Token(kakaoOAuth2TokenForm);

        if (oAuth2TokenResponse.getData() == null || oAuth2TokenResponse.getError() != null) {
            throw new CustomException(
                    ErrorCodes.INTERNAL_SERVER_ERROR.getNumber(),
                    oAuth2TokenResponse.getError().getOrDefault("msg", ErrorCodes.INTERNAL_SERVER_ERROR.getMessage())
            );
        }

        return oAuth2TokenResponse.getData();
    }

    public KakaoOAuth2User getKakaoOAuth2User(String accessToken) {

        KakaoResponse<KakaoOAuth2User> kakaoOAuth2UserResponse = kakaoRestClient.getKakaoOAuth2User(accessToken);

        if (kakaoOAuth2UserResponse.getData() == null || kakaoOAuth2UserResponse.getError() != null) {
            throw new CustomException(
                    ErrorCodes.INTERNAL_SERVER_ERROR.getNumber(),
                    kakaoOAuth2UserResponse.getError().getOrDefault("msg", ErrorCodes.INTERNAL_SERVER_ERROR.getMessage())
            );
        }

        return kakaoOAuth2UserResponse.getData();
    }
}
