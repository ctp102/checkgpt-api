package io.hexbit.api.controller;

import io.hexbit.api.config.annotation.Swagger200Response;
import io.hexbit.api.security.annotation.ClientRequest;
import io.hexbit.api.security.annotation.Secured;
import io.hexbit.api.security.resolver.WebRequest;
import io.hexbit.api.utils.JwtUtils;
import io.hexbit.core.common.response.CustomResponse;
import io.hexbit.core.oauth2.domain.KakaoOAuth2AuthorizationResponse;
import io.hexbit.core.oauth2.domain.KakaoOAuth2Token;
import io.hexbit.core.oauth2.domain.KakaoOAuth2User;
import io.hexbit.core.oauth2.dto.OAuth2RequestDto;
import io.hexbit.core.oauth2.enums.OAuth2ProviderTypes;
import io.hexbit.core.oauth2.form.KakaoOAuth2TokenForm;
import io.hexbit.core.oauth2.service.KakaoOAuth2Service;
import io.hexbit.core.oauth2.service.OAuth2Service;
import io.hexbit.core.oauth2.utils.OAuth2Utils;
import io.hexbit.core.user.domain.User;
import io.hexbit.core.user.dto.UserResponseDto;
import io.hexbit.core.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "OAuth2 Login API")
@Slf4j
@Controller
@RequiredArgsConstructor
public class OAuth2Controller {

    private final Map<String, OAuth2Service> oAuth2ServiceMap;
    private final KakaoOAuth2Service kakaoOAuth2Service;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    /**
     * AOS/IOS 전용
     */
    @Operation(summary = "회원 JWT 인증 토큰 생성")
    @Parameters({
            @Parameter(name = "webRequest", hidden = true),
            @Parameter(name = "oAuth2Provider", description = "OAuth2 Provider 이름", required = true, example = "kakao")
    })
    @Swagger200Response
    @Secured
    @ResponseBody
    @PostMapping(value = "/api/v1/oauth2/{oAuth2Provider}/auth-token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CustomResponse createJwt(
            @ClientRequest WebRequest webRequest,
            @Valid @RequestBody OAuth2RequestDto oAuth2RequestDto,
            @PathVariable String oAuth2Provider) {

        // 1. oAuth2Provider 검증
        OAuth2ProviderTypes.checkRegisteredOAuth2Provider(oAuth2Provider);
        OAuth2Service oAuth2Service = oAuth2ServiceMap.get(oAuth2Provider.toLowerCase() + "OAuth2Service");

        // 2. OAuth Resource Server로부터 회원 정보 조회
        User user = oAuth2Service.getOAuth2User(oAuth2RequestDto);

        // 3. 회원 가입 또는 회원 가입 패스 처리
        User savedUser = userService.joinUser(user);

        // 4. 회원 정보 기반의 jwt 토큰 생성
        String authToken = jwtUtils.createJwt(savedUser.getUserId(), savedUser.getEmail());

        UserResponseDto userResponseDto = UserResponseDto.of(savedUser);

        // 5. jwt 토큰 리턴
        return new CustomResponse.Builder()
                .addData("user", userResponseDto)
                .addData("authToken", authToken).build();
    }

    /**
     * 웹 전용
     */
    @GetMapping("/api/v1/oauth2/{oAuth2Provider}/authorize")
    public String getOAuth2AuthorizationURI(
            @ClientRequest WebRequest webRequest,
            @PathVariable String oAuth2Provider) {

        OAuth2ProviderTypes.checkRegisteredOAuth2Provider(oAuth2Provider);
        OAuth2Service oAuth2Service = oAuth2ServiceMap.get(oAuth2Provider + "OAuth2Service");

        String redirectURI = OAuth2Utils.getRedirectURI(webRequest.request(), oAuth2Provider, false);
        String oAuth2AuthorizationURI = oAuth2Service.getOAuth2AuthorizationURI(redirectURI);

        log.debug("[getOAuth2AuthorizationURI] redirectURI = {}, oAuth2AuthorizationURI = {}", redirectURI, oAuth2AuthorizationURI);
        return "redirect:" + oAuth2AuthorizationURI;
    }

    /**
     * 웹 전용
     */
    @GetMapping("/api/v1/oauth2/kakao/callback")
    public String kakaoLoginAndJoin(
            @ClientRequest WebRequest webRequest,
            Model model,
            @ModelAttribute KakaoOAuth2AuthorizationResponse kakaoOAuth2AuthorizationResponse) {

        if (StringUtils.isNotBlank(kakaoOAuth2AuthorizationResponse.getError())) {
            log.error("[kakaoOAuth2AuthorizationResponse] errorCode = {}, errorMsg = {}", kakaoOAuth2AuthorizationResponse.getError(), kakaoOAuth2AuthorizationResponse.getErrorDescription());
            return "profile";
        }

        String encodedRedirectURI = OAuth2Utils.getRedirectURI(webRequest.request(), OAuth2ProviderTypes.KAKAO.name().toLowerCase(), true);
        KakaoOAuth2TokenForm kakaoOAuth2TokenForm = new KakaoOAuth2TokenForm();
        kakaoOAuth2TokenForm.setRedirect_uri(encodedRedirectURI);
        kakaoOAuth2TokenForm.setCode(kakaoOAuth2AuthorizationResponse.getCode());

        KakaoOAuth2Token kakaoOAuth2Token = kakaoOAuth2Service.getOAuth2Token(kakaoOAuth2TokenForm);

        if (kakaoOAuth2Token == null) {
            log.error("[kakaoOAuth2Token] kakaoOAuth2Token is null");
            return "profile";
        }

        KakaoOAuth2User kakaoOAuth2User = kakaoOAuth2Service.getKakaoOAuth2User(kakaoOAuth2Token.getAccessToken());

        User user = User.builder()
                .providerId(kakaoOAuth2User.getId())
                .provider(OAuth2ProviderTypes.KAKAO.name().toLowerCase())
                .email(kakaoOAuth2User.getKakaoAccount().getEmail())
                .nickName(kakaoOAuth2User.getKakaoAccount().getProfile().getNickname())
                .build();

        userService.joinUser(user);

        model.addAttribute("kakaoOAuth2Token", kakaoOAuth2Token);
        model.addAttribute("kakaoOAuth2User", kakaoOAuth2User);

        return "profile";
    }

}
