package com.example.api.security.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class WebRequest {

    private final HttpServletRequest request;
    private final Map<String, String> clientMap;
//    private final ApiAuthToken apiAuthToken;
//    private final SessionDevice sessionDevice;

    public String getReferer() {
        return clientMap.get("Referer");
    }

    public Locale getLocale() {
        return new Locale(clientMap.get("locale"));
    }

    public String getUserAgent() {
        return clientMap.get("User-Agent");
    }

    public Long getUserId() {
        return null;
    }

}
