package io.hexbit.core.oauth2.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexbit.core.book.domain.KakaoBookSearchResponse;
import io.hexbit.core.book.enums.KakaoSearchApiTypes;
import io.hexbit.core.book.form.KakaoBookForm;
import io.hexbit.core.common.config.properties.KakaoOAuth2Properties;
import io.hexbit.core.oauth2.domain.KakaoOAuth2Token;
import io.hexbit.core.oauth2.domain.KakaoOAuth2User;
import io.hexbit.core.oauth2.domain.KakaoResponse;
import io.hexbit.core.oauth2.enums.KakaoLoginApiTypes;
import io.hexbit.core.oauth2.form.KakaoOAuth2TokenForm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.net.URI;

@Slf4j
@Getter
public class KakaoRestClient {

    private final KakaoOAuth2Properties kakaoOAuth2Properties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public KakaoRestClient(KakaoOAuth2Properties kakaoOAuth2Properties, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.kakaoOAuth2Properties = kakaoOAuth2Properties;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 카카오 OAuth2 AccessToken, RefreshToken 발급
     */
    public KakaoResponse<KakaoOAuth2Token> getOAuth2Token(KakaoOAuth2TokenForm kakaoOAuth2TokenForm) {
        kakaoOAuth2TokenForm.setClient_id(kakaoOAuth2Properties.getClientId());
        kakaoOAuth2TokenForm.setClient_secret(kakaoOAuth2Properties.getClientSecret());

        return post(KakaoLoginApiTypes.GET_TOKEN.getEndPoint(), kakaoOAuth2TokenForm, KakaoOAuth2Token.class);
    }

    /**
     * 카카오 OAuth2 사용자 정보 조회
     */
    public KakaoResponse<KakaoOAuth2User> getKakaoOAuth2User(String accessToken) {
        URI endPoint = UriComponentsBuilder
                .fromUriString(KakaoLoginApiTypes.GET_USER_INFO.getEndPoint())
                .queryParam("secure_resource", true) // 이미지 URL 값 HTTPS 여부, true 설정 시 HTTPS 사용, 기본 값 false
                .encode()
                .build()
                .toUri();

        HttpHeaders httpHeaders = getHttpHeadersForLogin(accessToken);

        return get(endPoint, httpHeaders, KakaoOAuth2User.class);
    }

    /**
     * 카카오 도서 검색
     */
    public KakaoResponse<KakaoBookSearchResponse> getKakaoSearchBook(KakaoBookForm kakaoBookForm, Pageable pageable) {
        URI endPoint = UriComponentsBuilder
                .fromUriString(KakaoSearchApiTypes.SEARCH_BOOK.getEndPoint())
                .queryParam("query",    kakaoBookForm.getQuery())
                .queryParam("sort",     kakaoBookForm.getSort())
                .queryParam("target",   kakaoBookForm.getTarget())
                .queryParam("page",     pageable.getPageNumber() + 1)
                .queryParam("size",     pageable.getPageSize())
                .encode()
                .build()
                .toUri();

        HttpHeaders httpHeaders = getHttpHeadersForSearch(kakaoOAuth2Properties.getClientId());
        return get(endPoint, httpHeaders, KakaoBookSearchResponse.class);
    }

    public <T> KakaoResponse<T> get(URI uri, HttpHeaders httpHeaders, Class<T> responseType) {
        KakaoResponse<T> kakaoResponse = new KakaoResponse<>();

        RequestEntity<?> requestEntity = new RequestEntity<>(httpHeaders, HttpMethod.GET, uri);

        log.info("[GET] REST CALL: {}", uri);

        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );

            if (isNotEmpty(responseEntity)) {
                kakaoResponse.setData(objectMapper.convertValue(responseEntity.getBody(), responseType));
            }
        } catch (HttpStatusCodeException e) {
            log.error("[HttpStatusCodeException]: {} ", e.getMessage());
            try {
                kakaoResponse.setError(objectMapper.readValue(e.getResponseBodyAsString(), responseType));
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            log.error("[Exception] ", e);
        }
        return kakaoResponse;
    }

    // TODO: 2023/09/24  kakaoOAuth2TokenForm 이것만 받는데 전체적으로 받을 수 있도록 개선하기
    public <T> KakaoResponse<T> post(String endPoint, KakaoOAuth2TokenForm kakaoOAuth2TokenForm, Class<T> responseType) {

        HttpHeaders httpHeaders = getHttpHeadersForLogin();
        MultiValueMap<String, String> body = convertToMultiValueMap(kakaoOAuth2TokenForm);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, httpHeaders);

        KakaoResponse<T> kakaoResponse = new KakaoResponse<>();

        log.info("[POST] REST CALL: {}", endPoint);

        try {
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    endPoint,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );

            if (isNotEmpty(responseEntity)) {
                kakaoResponse.setData(objectMapper.convertValue(responseEntity.getBody(), responseType));
            }
        } catch (HttpStatusCodeException e) {
            log.error("[HttpStatusCodeException]: {} ", e.getMessage());
            try {
                kakaoResponse.setError(objectMapper.readValue(e.getResponseBodyAsString(), responseType));
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            log.error("[Exception] ", e);
        }

        return kakaoResponse;
    }

    private boolean isNotEmpty(ResponseEntity<?> response) {
        return response != null && response.getBody() != null && response.getStatusCode() == HttpStatus.OK;
    }

    private HttpHeaders getHttpHeadersForLogin() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return httpHeaders;
    }

    private HttpHeaders getHttpHeadersForLogin(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBearerAuth(accessToken);
        return httpHeaders;
    }

    private HttpHeaders getHttpHeadersForSearch(String restApiKey) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "KakaoAK " + restApiKey);
        return httpHeaders;
    }

    private <T> MultiValueMap<String, String> convertToMultiValueMap(T object) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String snakeCaseFieldName = convertCamelCaseToSnakeCase(fieldName);

            try {
                Object fieldValue = field.get(object);
                if (fieldValue != null) {
                    multiValueMap.add(snakeCaseFieldName, fieldValue.toString());
                }
            } catch (IllegalAccessException e) {
                log.error("[convertToSnakeCase] IllegalAccessException", e);
            }
        }
        return multiValueMap;
    }

    private String convertCamelCaseToSnakeCase(String camelCase) {
        StringBuilder snakeCase = new StringBuilder();
        for (char c : camelCase.toCharArray()) {
            if (Character.isUpperCase(c)) {
                snakeCase.append('_').append(Character.toLowerCase(c));
            } else {
                snakeCase.append(c);
            }
        }
        return snakeCase.toString();
    }

}
