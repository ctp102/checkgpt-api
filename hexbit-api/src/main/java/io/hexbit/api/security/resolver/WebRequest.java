package io.hexbit.api.security.resolver;

import io.hexbit.api.security.domain.AuthTokenWrapper;
import io.hexbit.core.user.domain.UserSessionDevice;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class WebRequest {

    private final HttpServletRequest request;
    private final Map<String, String> clientMap;
    private final AuthTokenWrapper authTokenWrapper;
    private final UserSessionDevice userSessionDevice;

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
