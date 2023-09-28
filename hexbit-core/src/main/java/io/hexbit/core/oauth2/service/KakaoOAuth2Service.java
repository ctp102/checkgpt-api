package io.hexbit.core.oauth2.service;

import io.hexbit.core.oauth2.domain.KakaoOAuth2Response;
import io.hexbit.core.oauth2.domain.KakaoOAuth2Token;
import io.hexbit.core.oauth2.domain.KakaoOAuth2User;
import io.hexbit.core.oauth2.enums.KakaoApiTypes;
import io.hexbit.core.oauth2.form.KakaoOAuth2TokenForm;
import io.hexbit.core.oauth2.restclient.KakaoRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuth2Service implements OAuth2Service {

    private final KakaoRestClient kakaoRestClient;

    public String getOAuth2AuthorizationURI(String redirectURI) {
        return UriComponentsBuilder.fromUriString(KakaoApiTypes.GET_AUTHORIZATION_CODE.getEndPoint())
                .queryParam("client_id", kakaoRestClient.getKakaoOAuth2Properties().getClientId())
                .queryParam("redirect_uri", redirectURI)
                .queryParam("response_type", "code")
                .queryParam("scope", "profile_nickname, account_email, age_range")
                .toUriString();
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
