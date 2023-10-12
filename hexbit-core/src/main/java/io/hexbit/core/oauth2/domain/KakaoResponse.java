package io.hexbit.core.oauth2.domain;

import lombok.Data;

@Data
public class KakaoResponse<T> {

    private T data;
    private Object error;

}
