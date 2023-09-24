package com.example.core.oauth2.domain;

import lombok.Data;

@Data
public class KakaoOAuth2Response<T> {

    private T data;
    private Object error;

}
