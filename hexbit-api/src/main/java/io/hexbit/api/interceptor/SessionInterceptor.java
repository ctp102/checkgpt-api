package io.hexbit.api.interceptor;

import io.hexbit.api.interceptor.annotation.InterceptorExclude;
import io.hexbit.api.security.domain.AuthTokenWrapper;
import io.hexbit.api.utils.WebAuthUtils;
import io.hexbit.api.utils.WebUtils;
import io.hexbit.core.user.domain.UserSessionDevice;
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

            // Client JWT 파싱
            String clientBearerJwt = webAuthUtils.getAuthorization(request);
            if (clientBearerJwt != null) {
                AuthTokenWrapper authTokenWrapper = webAuthUtils.parseAuthToken(clientBearerJwt);
                request.setAttribute("authTokenWrapper", authTokenWrapper);
            }

            // User 접속 Device
            UserSessionDevice userSessionDevice = webAuthUtils.getUserSessionDevice(request);
            request.setAttribute("userSessionDevice", userSessionDevice);

            String clientIp = WebUtils.getClientIpAddr(request);
            String userAgent = webAuthUtils.getUserAgent(request);
            String requestUrl = request.getRequestURL().append(WebUtils.getRequestQueryString(request)).toString();
            log.info("[APIS][{}] Request URL : {}, IP : {}, User-Agent : {}", request.getMethod(), requestUrl, clientIp, userAgent);

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
