package com.example.api.interceptor;

import com.example.api.interceptor.annotation.InterceptorExclude;
import com.example.api.utils.WebAuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

    private final WebAuthUtils webAuthUtils;

    public SessionInterceptor(ApplicationContext applicationContext) {
        this.webAuthUtils = applicationContext.getBean(WebAuthUtils.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.getMethodAnnotation(InterceptorExclude.class) != null) {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }

            // User 접속 Device
//            SessionDevice sessionDevice = webAuthUtils.getSessionDevice(request);
//            request.setAttribute("sessionDevice", sessionDevice);

            // 기본 api key
//            ApiAuthToken apiAuthToken = webAuthUtils.getApiAuthToken(request);
//            request.setAttribute("apiAuthToken", apiAuthToken);

            // 언어 설정
            String language = webAuthUtils.getAppLanguage(request);
            request.setAttribute("language", language);

            // 로케일 언어 설정
            String locale = webAuthUtils.getLocale(request);
            request.setAttribute("locale", locale);

//            String clientIpAddr = GearWebUtils.getClientIpAddr(request);
//            String userAgent = request.getHeader("User-Agent");
//            String requestUrl = request.getRequestURL().append(GearWebUtils.getRequestQueryString(request)).toString();
//            log.info("[APIS][{}] Request URL : {}, IP : {}, User-Agent : {}", request.getMethod(), requestUrl, clientIpAddr, userAgent);
//
            // TODO: [2023/09/28, jh.cho] 추후 사용자 세션 로그 저장
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            log.error("afterCompletion Error : ", ex);
        }
    }

}
