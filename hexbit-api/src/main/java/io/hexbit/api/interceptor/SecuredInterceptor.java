package io.hexbit.api.interceptor;

import io.hexbit.api.interceptor.annotation.InterceptorExclude;
import io.hexbit.api.security.annotation.Secured;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class SecuredInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.getMethodAnnotation(InterceptorExclude.class) != null) {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }

            checkSecured(handlerMethod);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void checkSecured(HandlerMethod handlerMethod) {
        Secured secured = handlerMethod.getMethodAnnotation(Secured.class);

        if (secured != null) {
            // TODO: [2023/09/29, jh.cho] 추후 인증 처리
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
