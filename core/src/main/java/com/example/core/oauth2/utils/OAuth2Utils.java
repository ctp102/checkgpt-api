package com.example.core.oauth2.utils;

import com.example.core.common.exception.CustomBadRequestException;
import com.example.core.oauth2.enums.OAuth2ProviderTypes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.core.common.enums.ErrorCodes.NOT_MATCH_OAUTH2_PROVIDER;

public class OAuth2Utils {

    public static String getRedirectURI(HttpServletRequest request, String oAuth2Provider, boolean encode) {

        OAuth2ProviderTypes oAuth2ProviderTypes = OAuth2ProviderTypes.findByName(oAuth2Provider);
        if (oAuth2ProviderTypes == null) {
            throw new CustomBadRequestException(NOT_MATCH_OAUTH2_PROVIDER.getNumber(), NOT_MATCH_OAUTH2_PROVIDER.getMessage());
        }

        if (encode) {
            return UriComponentsBuilder
                    .fromUriString(getRequestServerProtocolDomain(request))
                    .path("/api/v1/oauth2/" + oAuth2Provider + "/callback")
                    .build()
                    .encode()
                    .toUriString();
        }

        return UriComponentsBuilder
                .fromUriString(getRequestServerProtocolDomain(request))
                .path("/api/v1/oauth2/" + oAuth2Provider + "/callback")
                .build()
                .toUriString();
    }

    public static String getRequestServerProtocolDomain(HttpServletRequest request) {
        String xForwardedProto = request.getHeader("X-Forwarded-Proto");
        String xForwardedPort = request.getHeader("X-Forwarded-Port");

        // protocol
        if (xForwardedProto == null) {
            String protocol = request.getProtocol(); // ex) HTTP/1.1
            xForwardedProto = protocol.substring(0, protocol.indexOf("/")).toLowerCase();
        }

        // domain
        String commonUrlStr = xForwardedProto + "://" + request.getServerName();

        // port
        if (xForwardedPort == null) {
            xForwardedPort = String.valueOf(request.getServerPort());
        }

        if (!xForwardedPort.equals("80") && !xForwardedPort.equals("443")) {
            commonUrlStr = commonUrlStr + ":" + xForwardedPort;
        }
        return commonUrlStr;
    }

}
