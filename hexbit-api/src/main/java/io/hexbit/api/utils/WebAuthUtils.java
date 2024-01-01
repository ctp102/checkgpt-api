package io.hexbit.api.utils;

import io.hexbit.api.security.domain.AuthTokenWrapper;
import io.hexbit.core.user.domain.UserSessionDevice;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebAuthUtils {

    private final JwtUtils jwtUtils;
    private final WebUtils webUtils;

    public String getApiKey(HttpServletRequest request) {
        return (request.getHeader("Api-Key") != null) ? request.getHeader("Api-Key") : request.getParameter("apiKey");
    }

//    public ApiAuthToken getApiAuthToken(HttpServletRequest request) {
//        String apiKey = getApiKey(request);
//        String userAgent = request.getHeader("User-Agent");
//
//        if (!StringUtils.containsIgnoreCase(userAgent, "postman") && StringUtils.equals(apiKey, "1hbah5t41ck6u1jy5g3h8yjr6u1hruaa")) {
//            return null;
//        }
//
//        return getApiAuthToken(apiKey);
//    }

//    public ApiAuthToken getApiAuthToken(String apiKey) {
//        return (apiKey != null) ? apiAuthTokens.stream().filter(x -> x.getApiKey().equals(apiKey)).findFirst().orElse(null) : null;
//    }

    public UserSessionDevice getUserSessionDevice(HttpServletRequest request) {
        return UserSessionDevice.builder()
                .deviceUUID(getAppDeviceUuid(request))
                .deviceToken(getAppDeviceToken(request))
                .deviceLastAccessIp(WebUtils.getClientIpAddr(request))
                .deviceLangCd(getAppLanguage(request))
                .deviceAppVersion(getAppVersion(request))
                .userAgent(getUserAgent(request))
                .build();
    }

    public String getAppVersion(HttpServletRequest request) {
        return StringUtils.defaultIfBlank(getHeader(request, "App-Version"), "0.0.1");
    }

    public String getAppLanguage(HttpServletRequest request) {
        return StringUtils.defaultIfBlank(getHeader(request, "App-Language"), "en");
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
        return getHeader(request, HttpHeaders.AUTHORIZATION);
    }

    public String getUserAgent(HttpServletRequest request) {
        return StringUtils.defaultIfBlank(getHeader(request, HttpHeaders.USER_AGENT), "Unknown");
    }

    public String getHeader(HttpServletRequest request, String key) {
        String value = getHeaderValue(request, key);
        if (StringUtils.isBlank(value)) {
            value = getCookieValue(request, key);
        }
        return value;
    }

    private String getHeaderValue(HttpServletRequest request, String key) {
        String value = request.getHeader(key);
        if (value == null || "undefined".equals(value)) {
            return null;
        }
        return value;
    }

    private String getCookieValue(HttpServletRequest request, String key) {
        Cookie tokenCookie = WebUtils.getCookie(request, key);
        if (tokenCookie != null && tokenCookie.getValue() != null) {
            return tokenCookie.getValue();
        }
        return null;
    }

    @Deprecated
    public AuthTokenWrapper parseAuthToken(HttpServletRequest request) {
        String token = getAuthToken(request);
        if (token == null) {
            return null;
        }
        return jwtUtils.parseAuthToken(token);
    }

    public AuthTokenWrapper parseAuthToken(String bearerToken) {
        bearerToken = StringUtils.removeStart(bearerToken, "Bearer ");
        return jwtUtils.parseAuthToken(bearerToken);
    }

}
