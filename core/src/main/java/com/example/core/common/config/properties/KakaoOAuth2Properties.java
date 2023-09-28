package com.example.core.common.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationProperties("oauth2.kakao")
@ConfigurationPropertiesBinding
@Getter
@AllArgsConstructor
public class KakaoOAuth2Properties {

    private String clientId;
    private String clientSecret;

}