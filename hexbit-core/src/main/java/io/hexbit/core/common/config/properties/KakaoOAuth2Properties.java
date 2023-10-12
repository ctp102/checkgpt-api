package io.hexbit.core.common.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationProperties("oauth2.kakao")
@ConfigurationPropertiesBinding
@Getter
@AllArgsConstructor
public class KakaoOAuth2Properties {

    private String clientId; // restApiKey
    private String clientSecret;

}
