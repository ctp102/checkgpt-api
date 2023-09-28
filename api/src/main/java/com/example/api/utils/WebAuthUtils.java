package com.example.api.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

public class WebAuthUtils {

    @Getter
    private final List<ApiAuthTokenDomain> apiAuthTokens;
    @Autowired
    private GearJwtUtils gearJwtUtils;

    public WebAuthUtils(ApiAuthTokenConfig apiAuthTokenConfig) {
        apiAuthTokens = apiAuthTokenConfig.getTokens()
                .stream()
                .map(token -> new ApiAuthTokenDomain(token.getApiKey(), token.getAppName(), token.getApiLevel()))
                .collect(Collectors.toList());
    }

    public String getApiKey(HttpServletRequest request) {
        return (request.getHeader("Api-Key") != null) ? request.getHeader("Api-Key") : request.getParameter("apiKey");
    }

    public ApiAuthToken getApiAuthToken(HttpServletRequest request) {
        String apiKey = getApiKey(request);
        String userAgent = request.getHeader("User-Agent");

        if (!StringUtils.containsIgnoreCase(userAgent, "postman") && StringUtils.equals(apiKey, "1hbah5t41ck6u1jy5g3h8yjr6u1hruaa")) {
            return null;
        }

        return getApiAuthToken(apiKey);
    }

    public ApiAuthToken getApiAuthToken(String apiKey) {
        return (apiKey != null) ? apiAuthTokens.stream().filter(x -> x.getApiKey().equals(apiKey)).findFirst().orElse(null) : null;
    }

    public FrontAuthToken getFrontAuthToken(HttpServletRequest request) {
        String jwtToken = getHeader(request, encodeAuthTokenName("FRONTAUTH")); // RlJPTlRBVVRI
        if (jwtToken == null) {
            return null;
        }
        return gearJwtUtils.parseFrontJwt(jwtToken);
    }

    public SessionDevice getSessionDevice(HttpServletRequest request) {

        SessionDeviceDomain sessionDeviceDomain = new SessionDeviceDomain();
        sessionDeviceDomain.setAppVersion(getAppVersion(request));
        sessionDeviceDomain.setUserAgent(request.getHeader("User-Agent"));

        return sessionDeviceDomain;
    }

    public String getAuthorizationBearerJwt(HttpServletRequest request) {
        String authorizationHeader = getHeader(request, "Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        // "Bearer " 다음 문자열부터 JWT 추출
        return authorizationHeader.substring(7);
    }

    public String getAppVersion(HttpServletRequest request) {
        return StringUtils.defaultIfBlank(getHeader(request, "App-Version"), "0.1.0");
    }

    public String getAppLanguage(HttpServletRequest request) {
        return StringUtils.defaultIfBlank(getHeader(request, "App-Language"), "en");
    }

    public String getLocale(HttpServletRequest request) {
        return StringUtils.defaultIfBlank(getHeader(request, "locale"), "en");
    }

    public String getAppDeviceUuid(HttpServletRequest request) {
        return StringUtils.defaultIfBlank(getHeader(request, "App-Device-Udid"), getHeader(request, "App-Device-Uuid"));
    }

    public String getAppDeviceToken(HttpServletRequest request) {
        return getHeader(request, "App-Device-Token");
    }

    public String getAuthToken(HttpServletRequest request) {
        return getHeader(request, "Auth-Token");
    }

    public String getAuthorization(HttpServletRequest request) {
        return getHeader(request, "Authorization");
    }

    public String getAppDeviceCurrency(HttpServletRequest request) {
        return StringUtils.defaultIfBlank(getHeader(request, "App-Device-Currency"), "USD").toUpperCase();
    }

    public String getHeader(HttpServletRequest request, String key) {
        String token = getHeaderValue(request, key);
        if (StringUtils.isBlank(token)) {
            token = getCookieValue(request, key);
        }
        return token;
    }

    private String getHeaderValue(HttpServletRequest request, String key) {
        String value = request.getHeader(key);
        if (value == null || "undefined".equals(value)) {
            return null;
        }
        return value;
    }

    private String getCookieValue(HttpServletRequest request, String key) {
        Cookie tokenCookie = GearWebUtils.getCookie(request, key);
        if (tokenCookie != null && tokenCookie.getValue() != null) {
            return tokenCookie.getValue();
        }
        return null;
    }

}
