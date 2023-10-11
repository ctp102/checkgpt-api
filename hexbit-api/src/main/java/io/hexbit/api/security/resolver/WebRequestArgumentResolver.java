package io.hexbit.api.security.resolver;

import io.hexbit.api.security.annotation.ClientRequest;
import io.hexbit.api.security.domain.AuthTokenWrapper;
import io.hexbit.core.user.domain.UserSessionDevice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;

public class WebRequestArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ClientRequest.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (parameter.getParameterType().equals(WebRequest.class)) {

            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            Map<String, String> clientMap = getClientMap(request);

            AuthTokenWrapper authTokenWrapper = (AuthTokenWrapper) request.getAttribute("authTokenWrapper");
            UserSessionDevice userSessionDevice = (UserSessionDevice) request.getAttribute("userSessionDevice");

            return new WebRequest(request, clientMap, authTokenWrapper, userSessionDevice);
        }
        return null;
    }

    private Map<String, String> getClientMap(HttpServletRequest request) {
        Map<String, String> clientMap = new HashMap<>();

        if (request.getHeader("Referer") != null) {
            clientMap.put("Referer", request.getHeader("Referer"));
        }

        if (request.getHeader("User-Agent") != null) {
            clientMap.put("User-Agent", request.getHeader("User-Agent"));
        }

        return clientMap;
    }

}
