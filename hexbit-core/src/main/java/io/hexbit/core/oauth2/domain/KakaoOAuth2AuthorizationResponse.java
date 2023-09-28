package io.hexbit.core.oauth2.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoOAuth2AuthorizationResponse {

    private String code;
    private String state; // 요청 시 전달한 state 값과 동일한 값

    private String error; // 인증 실패 시 반환되는 에러 코드
    private String errorDescription; // 인증 실패 시 반환되는 에러 메시지

}
