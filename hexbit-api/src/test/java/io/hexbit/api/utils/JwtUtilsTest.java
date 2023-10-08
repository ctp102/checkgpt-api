package io.hexbit.api.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Base64;

@Slf4j
@ActiveProfiles("local")
class JwtUtilsTest {
    
    @Test
    @DisplayName("HMAC-SHA256 키를 생성한다.")
    void createHmacSha256Key() {
        // given
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

        // when
        String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);

        // then
        Assertions.assertThat(base64EncodedKey).isNotNull();
        log.info("base64EncodedKey: {}", base64EncodedKey);
    }

}