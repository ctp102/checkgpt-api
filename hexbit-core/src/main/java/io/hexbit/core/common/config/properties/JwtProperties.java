package io.hexbit.core.common.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationProperties("jwt")
@ConfigurationPropertiesBinding
@Getter
@AllArgsConstructor
public class JwtProperties {

    private String accessPrivateKey;

}
