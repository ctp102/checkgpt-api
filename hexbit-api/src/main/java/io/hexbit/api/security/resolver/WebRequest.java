package io.hexbit.api.security.resolver;

import io.hexbit.api.security.domain.AuthTokenWrapper;
import io.hexbit.core.user.domain.UserSessionDevice;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public record WebRequest(
        HttpServletRequest request,
        Map<String, String> clientMap,
        AuthTokenWrapper authTokenWrapper,
        UserSessionDevice userSessionDevice
) {

    public String getReferer() {
        return clientMap.get("Referer");
    }

    public String getUserAgent() {
        return clientMap.get("User-Agent");
    }

    public Long getUserId() {
        return authTokenWrapper.getUserId();
    }

}
