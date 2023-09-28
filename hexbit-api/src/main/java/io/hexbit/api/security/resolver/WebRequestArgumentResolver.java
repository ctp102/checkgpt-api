package io.hexbit.api.security.resolver;

import io.hexbit.api.security.annotation.ClientRequest;
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

//            ApiAuthToken apiAuthToken = (ApiAuthToken) request.getAttribute("apiAuthToken");
//            SessionDevice sessionDevice = (SessionDevice) request.getAttribute("sessionDevice");

            return new WebRequest(request, clientMap);
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

        if (request.getAttribute("locale") != null) {
            clientMap.put("locale", (String) request.getAttribute("locale"));
        }

        return clientMap;
    }

}
