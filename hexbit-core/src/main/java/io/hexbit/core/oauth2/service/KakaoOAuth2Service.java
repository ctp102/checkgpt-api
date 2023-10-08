package io.hexbit.core.oauth2.service;

import io.hexbit.core.oauth2.domain.KakaoOAuth2Response;
import io.hexbit.core.oauth2.domain.KakaoOAuth2Token;
import io.hexbit.core.oauth2.domain.KakaoOAuth2User;
import io.hexbit.core.oauth2.dto.OAuth2RequestDto;
import io.hexbit.core.oauth2.enums.KakaoApiTypes;
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
        return UriComponentsBuilder.fromUriString(KakaoApiTypes.GET_AUTHORIZATION_CODE.getEndPoint())
                .queryParam("client_id", kakaoRestClient.getKakaoOAuth2Properties().getClientId())
                .queryParam("redirect_uri", redirectURI)
                .queryParam("response_type", "code")
                .queryParam("scope", "profile_nickname, profile_image, account_email")
                .toUriString();
    }

    @Override
    public User getOAuth2User(OAuth2RequestDto oAuth2RequestDto) {
        KakaoOAuth2User kakaoOAuth2User = getKakaoOAuth2User(oAuth2RequestDto.getAccessToken());

        return User.builder()
                .providerId(kakaoOAuth2User.getId())
                .provider(OAuth2ProviderTypes.KAKAO.name().toLowerCase())
                .email(kakaoOAuth2User.getKakaoAccount().getEmail())
                .nickName(kakaoOAuth2User.getKakaoAccount().getProfile().getNickname())
                .build();
    }

    public KakaoOAuth2Token getOAuth2Token(KakaoOAuth2TokenForm kakaoOAuth2TokenForm) {
        KakaoOAuth2Response<KakaoOAuth2Token> oAuth2TokenResponse = kakaoRestClient.getOAuth2Token(kakaoOAuth2TokenForm);
        return oAuth2TokenResponse.getData();
    }

    public KakaoOAuth2User getKakaoOAuth2User(String accessToken) {
        KakaoOAuth2Response<KakaoOAuth2User> kakaoOAuth2User = kakaoRestClient.getKakaoOAuth2User(accessToken);
        return kakaoOAuth2User.getData();
    }
}
