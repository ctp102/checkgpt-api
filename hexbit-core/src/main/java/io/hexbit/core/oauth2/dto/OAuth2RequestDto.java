package io.hexbit.core.oauth2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OAuth2RequestDto {

    @NotBlank(message = "AccessToken 누락")
    private String accessToken;

}
