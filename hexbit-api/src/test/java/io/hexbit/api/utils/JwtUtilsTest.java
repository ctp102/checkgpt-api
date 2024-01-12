package io.hexbit.api.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ActiveProfiles("local")
class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    @DisplayName("회원 정보 기반의 JWT를 생성한다.")
    void creatJwt() {
        // given
        Long userId = 10000L;
        String email = "test@test.com";

        // when
        String jwt = jwtUtils.createJwt(userId, email);

        // then
        assertThat(jwt).isNotNull();
        log.info("jwt: {}", jwt);
    }

    @Test
    @DisplayName("HMAC-SHA256 키를 생성한다.")
    void createHmacSha256Key() {
        // given
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

        // when
        String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);

        // then
        assertThat(base64EncodedKey).isNotNull();
        log.info("base64EncodedKey: {}", base64EncodedKey);
    }

}