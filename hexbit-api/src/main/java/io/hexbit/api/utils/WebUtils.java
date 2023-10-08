package io.hexbit.api.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class WebUtils {

    public static final int MAX_AGE_YEAR = 60 * 60 * 24 * 365;

    public static String getServerDomain(HttpServletRequest request) {

        String scheme = getRequestScheme(request);
        int port = getRequestServerPort(request);

        StringBuilder serverProtocol = new StringBuilder(scheme).append("://").append(request.getServerName());

        if (!(port == 80 || port == 443)) {
            serverProtocol.append(":").append(port);
        }

        return serverProtocol.toString();
    }

    public static String getRequestScheme(HttpServletRequest request) {
        if (request.getHeader("X-Forwarded-Proto") != null) {
            return request.getHeader("X-Forwarded-Proto");
        }
        return request.getScheme();
    }

    public static int getRequestServerPort(HttpServletRequest request) {
        if (request.getHeader("X-Forwarded-Port") != null) {
            return Integer.parseInt(request.getHeader("X-Forwarded-Port"));
        }
        return request.getServerPort();
    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || "127.0.0.1".equalsIgnoreCase(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "127.0.0.1".equalsIgnoreCase(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "127.0.0.1".equalsIgnoreCase(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "127.0.0.1".equalsIgnoreCase(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "127.0.0.1".equalsIgnoreCase(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    /**
     * GET, POST 케이스별로 QueryString 리턴
     *
     * @param request the request
     * @return string
     */
    public static String getRequestQueryString(HttpServletRequest request) {

        if ("GET".equals(request.getMethod())) {
            if (request.getQueryString() != null) {
                return "?" + request.getQueryString();
            }
            return "";
        }

        // 파라미터 쿼리 스트링을 구한다.
        StringBuilder queryString = new StringBuilder();
        Map<String, String[]> params = request.getParameterMap();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (String value : values) {
                queryString.append(queryString.toString().isEmpty() ? "?" : "&");
                queryString.append(key).append("=").append(value);
            }
        }
        return queryString.toString();
    }

    /**
     * Get request query string string.
     *
     * @param request     the request
     * @param removeParam the remove param
     * @return the string
     */
    public static String getRequestQueryString(HttpServletRequest request, String removeParam) {
        StringBuilder query = new StringBuilder();
        String[] keyVals = request.getQueryString().split("&");
        for (String keyVal : keyVals) {
            if (!keyVal.startsWith(removeParam + "=")) {
                query.append((query.toString().isEmpty()) ? "&" : "?").append(keyVals);
            }
        }
        return query.toString();
    }

    /**
     * Get request query string string.
     *
     * @param request      the request
     * @param replaceParam the replace param
     * @param replaceValue the replace value
     * @return the string
     */
    public static String getRequestQueryString(HttpServletRequest request, String replaceParam, String replaceValue) {
        StringBuilder query = new StringBuilder();
        String[] keyVals = request.getQueryString().split("&");
        for (String keyVal : keyVals) {
            if (!keyVal.startsWith(replaceParam + "=")) {
                query.append((query.toString().isEmpty()) ? "&" : "?").append(keyVals);
            } else {
                query.append(replaceParam).append("=").append(replaceValue);
            }
        }
        if (query.indexOf(replaceParam + "=") == -1) {
            query.append(replaceParam).append("=").append(replaceValue);
        }

        return query.toString();
    }

    public static String getHeader(HttpServletRequest request, String key) {
        String token = getHeaderValue(request, key);
        if (StringUtils.isBlank(token)) {
            token = getCookieValue(request, key);
        }
        return token;
    }

    private static String getHeaderValue(HttpServletRequest request, String key) {
        String value = request.getHeader(key);
        if (value == null || "undefined".equals(value)) {
            return null;
        }
        return value;
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        return org.springframework.web.util.WebUtils.getCookie(request, name);
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie tokenCookie = getCookie(request, name);
        if (tokenCookie != null && tokenCookie.getValue() != null) {
            return tokenCookie.getValue();
        }
        return null;
    }

    public static Cookie getCookie(HttpServletRequest request, String name, String domain) {
        Assert.notNull(request, "Request must not be null");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName()) && domain.equals(cookie.getDomain())) {
                    return cookie;
                }
            }
        }
        return org.springframework.web.util.WebUtils.getCookie(request, name);
    }

    public static String getCookieValue(HttpServletRequest request, String name, String domain) {
        Cookie tokenCookie = getCookie(request, name, domain);
        if (tokenCookie != null && tokenCookie.getValue() != null) {
            return tokenCookie.getValue();
        }
        return null;
    }

    public static void createCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) throws IOException {
        createCookie(request, response, name, value, MAX_AGE_YEAR);
    }

    public static void createCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) throws IOException {
        Cookie cookie = new Cookie(name, URLEncoder.encode(value, StandardCharsets.UTF_8));
        cookie.setDomain(request.getServerName());
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void createCookie(String domain, HttpServletResponse response, String name, String value) throws IOException {
        createCookie(domain, response, name, value, MAX_AGE_YEAR);
    }

    public static void createCookie(String domain, HttpServletResponse response, String name, String value, int maxAge) throws IOException {
        Cookie cookie = new Cookie(name, URLEncoder.encode(value, StandardCharsets.UTF_8));
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) throws IOException {
        createCookie(request, response, name, "", 0);
    }

    public static void removeCookie(String domain, HttpServletResponse response, String name) throws IOException {
        createCookie(domain, response, name, "", 0);
    }

}