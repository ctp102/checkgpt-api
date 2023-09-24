package com.example.api.controller;

import com.example.core.oauth2.domain.KakaoOAuth2AuthorizationResponse;
import com.example.core.oauth2.domain.KakaoOAuth2Token;
import com.example.core.oauth2.domain.KakaoOAuth2User;
import com.example.core.oauth2.enums.OAuth2ProviderTypes;
import com.example.core.oauth2.form.KakaoOAuth2TokenForm;
import com.example.core.oauth2.service.KakaoOAuth2Service;
import com.example.core.oauth2.utils.OAuth2Utils;
import com.example.core.user.domain.User;
import com.example.core.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final KakaoOAuth2Service kakaoOAuth2Service;
    private final UserService userService;

    @GetMapping("/api/v1/oauth2/{oAuth2Provider}/authorize")
    public String getOAuth2AuthorizationURI(@PathVariable String oAuth2Provider, HttpServletRequest request) {
        String redirectURI = OAuth2Utils.getRedirectURI(request, oAuth2Provider, false);
        String oAuth2AuthorizationURI = kakaoOAuth2Service.getOAuth2AuthorizationURI(redirectURI);

        log.debug("[getOAuth2AuthorizationURI] redirectURI = {}, oAuth2AuthorizationURI = {}", redirectURI, oAuth2AuthorizationURI);
        return "redirect:" + oAuth2AuthorizationURI;
    }

    @GetMapping("/api/v1/oauth2/kakao/callback")
    public String kakaoLoginAndJoin(HttpServletRequest request, Model model,
                              @ModelAttribute KakaoOAuth2AuthorizationResponse kakaoOAuth2AuthorizationResponse) {

        if (StringUtils.isNotBlank(kakaoOAuth2AuthorizationResponse.getError())) {
            log.error("[kakaoOAuth2AuthorizationResponse] errorCode = {}, errorMsg = {}", kakaoOAuth2AuthorizationResponse.getError(), kakaoOAuth2AuthorizationResponse.getErrorDescription());
            return "profile";
        }

        String encodedRedirectURI = OAuth2Utils.getRedirectURI(request, OAuth2ProviderTypes.KAKAO.name().toLowerCase(), true);
        KakaoOAuth2TokenForm kakaoOAuth2TokenForm = new KakaoOAuth2TokenForm();
        kakaoOAuth2TokenForm.setRedirect_uri(encodedRedirectURI);
        kakaoOAuth2TokenForm.setCode(kakaoOAuth2AuthorizationResponse.getCode());

        KakaoOAuth2Token kakaoOAuth2Token = kakaoOAuth2Service.getOAuth2Token(kakaoOAuth2TokenForm);

        if (kakaoOAuth2Token == null) {
            log.error("[kakaoOAuth2Token] kakaoOAuth2Token is null");
            return "profile";
        }

        KakaoOAuth2User kakaoOAuth2User = kakaoOAuth2Service.getKakaoOAuth2User(kakaoOAuth2Token.getAccessToken());

        User user = User.toEntity(
                kakaoOAuth2User.getId(),
                OAuth2ProviderTypes.KAKAO.name().toLowerCase(),
                kakaoOAuth2User.getKakaoAccount().getEmail(),
                kakaoOAuth2User.getKakaoAccount().getProfile().getNickname()
        );

        userService.joinUser(user);

        model.addAttribute("kakaoOAuth2Token", kakaoOAuth2Token);
        model.addAttribute("kakaoOAuth2User", kakaoOAuth2User);

        return "profile";
    }

}
