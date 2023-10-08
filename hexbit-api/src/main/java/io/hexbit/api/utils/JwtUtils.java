package io.hexbit.api.utils;

import io.hexbit.api.security.domain.AuthTokenWrapper;
import io.hexbit.core.common.config.properties.JwtProperties;
import io.hexbit.core.common.enums.ErrorCodes;
import io.hexbit.core.common.exception.CustomBadRequestException;
import io.hexbit.core.common.exception.CustomException;
import io.hexbit.core.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtils {

    private final Key accessPrivateKey;

    public JwtUtils(JwtProperties jwtProperties) {
        this.accessPrivateKey = getPrivateKey(jwtProperties.getAccessPrivateKey());
    }

    private static final long ONE_DAY = 1000L * 60 * 60 * 24;

    public String createJwt(User user) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + ONE_DAY;
        Date expirationDate = new Date(expMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.ID, UUID.randomUUID().toString());
        claims.put(Claims.ISSUED_AT, new Date(nowMillis));
        claims.put(Claims.EXPIRATION, expirationDate);
        claims.put("userId", user.getUserId());
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .claims(claims)
                .signWith(accessPrivateKey)
                .compact();
    }

    public AuthTokenWrapper parseAuthToken(String token) {

        Claims claims = extractClaims(token, accessPrivateKey);
        if (claims == null) {
            return null;
        }

        return AuthTokenWrapper.of(token, claims);
    }

    private Key getPrivateKey(String privateKey) {
        return Keys.hmacShaKeyFor(privateKey.getBytes(StandardCharsets.UTF_8));
    }

    private Claims extractClaims(String token, Key privateKey) {
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) privateKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.error("[isValidToken] 토큰 세션 만료: {}", token, e);
            throw new CustomBadRequestException(ErrorCodes.JWT_TOKEN_EXPIRED.getNumber(), ErrorCodes.JWT_TOKEN_EXPIRED.getMessage());
        } catch (SignatureException e) {
            log.error("[isValidToken] 시그니처 예외: {}", token, e);
            throw new CustomBadRequestException(ErrorCodes.JWT_TOKEN_SIGNATURE_ERROR.getNumber(), ErrorCodes.JWT_TOKEN_SIGNATURE_ERROR.getMessage());
        } catch (MalformedJwtException e) {
            log.error("[isValidToken] 조작된 토큰: {}", token, e);
            throw new CustomBadRequestException(ErrorCodes.JWT_TOKEN_MALFORMED.getNumber(), ErrorCodes.JWT_TOKEN_MALFORMED.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("[isValidToken] 지원하지 않는 토큰: {}", token, e);
            throw new CustomBadRequestException(ErrorCodes.JWT_TOKEN_NOT_SUPPORTED.getNumber(), ErrorCodes.JWT_TOKEN_NOT_SUPPORTED.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("[isValidToken] 매개변수 예외: {}", token, e);
            throw new CustomBadRequestException(ErrorCodes.JWT_TOKEN_ILLEGAL_ARGUMENT.getNumber(), ErrorCodes.JWT_TOKEN_ILLEGAL_ARGUMENT.getMessage());
        } catch (JwtException e) {
            log.error("[isValidToken] Invalid token: {}", token, e);
            throw new CustomBadRequestException(ErrorCodes.JWT_TOKEN_EXCEPTION.getNumber(), ErrorCodes.JWT_TOKEN_EXCEPTION.getMessage());
        } catch (Exception e) {
            log.error("Failed to decrypt JWT token", e);
            throw new CustomException(ErrorCodes.INTERNAL_SERVER_ERROR.getNumber(), ErrorCodes.INTERNAL_SERVER_ERROR.getMessage());
        }
    }

}
