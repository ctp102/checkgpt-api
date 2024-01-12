package io.hexbit.core.oauth2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OAuth2RequestDto {

    @NotBlank(message = "AccessToken 누락")
    @Schema(description = "ex) 카카오톡으로부터 발급받은 액세스 토큰")
    private String accessToken;

}
