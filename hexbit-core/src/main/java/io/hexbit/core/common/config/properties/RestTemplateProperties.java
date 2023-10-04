package io.hexbit.core.common.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationProperties("rest.template")
@ConfigurationPropertiesBinding
@Getter
@AllArgsConstructor
public class RestTemplateProperties {

    private ConnectionManager connectionManager;
    private ClientHttpRequest clientHttpRequest;

    @Getter
    @Setter
    public static class ConnectionManager {
        private int defaultMaxPerRoute;
        private int maxTotal;
    }

    @Getter
    @Setter
    public static class ClientHttpRequest {
        private int evictIdleConnections;
        private int connectTimeout;
        private int readTimeout;
    }

}

