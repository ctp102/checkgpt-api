package io.hexbit.core.oauth2.domain;

import lombok.Data;

import java.util.Map;

@Data
public class KakaoResponse<T> {

    private T data;
    private Map<String, String> error;

}
