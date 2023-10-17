package io.hexbit.core.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.hexbit.core.common.config.properties.KakaoOAuth2Properties;
import io.hexbit.core.common.config.properties.RestTemplateProperties;
import io.hexbit.core.common.jackson.StringStripJsonDeserializer;
import io.hexbit.core.oauth2.restclient.KakaoRestClient;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CoreConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateProperties restTemplateProperties) {

        // 1. ClientHttpRequestFactory requestFactory
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(restTemplateProperties.getConnectionManager().getDefaultMaxPerRoute());
        connectionManager.setMaxTotal(restTemplateProperties.getConnectionManager().getMaxTotal());


        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
//                .evictIdleConnections(restTemplateProperties.getClientHttpRequest().getEvictIdleConnections(), TimeUnit.SECONDS)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        factory.setHttpClient(httpClient);
        factory.setConnectTimeout(restTemplateProperties.getClientHttpRequest().getConnectTimeout());

        // 2. MessageConverters
        MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper());
        jsonHttpMessageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));
        jsonHttpMessageConverter.setPrefixJson(false);

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringConverter.setWriteAcceptCharset(false);

        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();

        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.setCharset(StandardCharsets.UTF_8);

        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
        httpMessageConverters.add(jsonHttpMessageConverter);
        httpMessageConverters.add(stringConverter);
        httpMessageConverters.add(byteArrayHttpMessageConverter);
        httpMessageConverters.add(formHttpMessageConverter);

        // 3. RestTemplate
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setMessageConverters(httpMessageConverters);
        return restTemplate;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(customJsonDeserializeModule());
        return objectMapper;
    }

    private SimpleModule customJsonDeserializeModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringStripJsonDeserializer());
        return module;
    }

    @Bean
    public KakaoRestClient kakaoRestClient(KakaoOAuth2Properties kakaoOAuth2Properties, RestTemplate restTemplate, ObjectMapper objectMapper) {
        return new KakaoRestClient(kakaoOAuth2Properties, restTemplate, objectMapper);
    }

}
