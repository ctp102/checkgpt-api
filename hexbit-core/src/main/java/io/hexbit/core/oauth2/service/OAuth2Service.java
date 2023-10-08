package io.hexbit.core.oauth2.service;

import io.hexbit.core.oauth2.dto.OAuth2RequestDto;
import io.hexbit.core.user.domain.User;

public interface OAuth2Service {

    String getOAuth2AuthorizationURI(String callbackURI);

    User getOAuth2User(OAuth2RequestDto oAuth2RequestDto);
}
