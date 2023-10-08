package io.hexbit.api.security.domain;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthTokenWrapper {

    private String token;
    private String jti;
    private Long userId;
    private String userEmail;

    public static AuthTokenWrapper of(String token, Claims claims) {
        AuthTokenWrapper authTokenWrapper = new AuthTokenWrapper();
        authTokenWrapper.setToken(token);
        authTokenWrapper.setJti(claims.getId());
        authTokenWrapper.setUserId(claims.get("userId", Long.class));
        authTokenWrapper.setUserEmail(claims.get("email", String.class));
        return authTokenWrapper;
    }

}
