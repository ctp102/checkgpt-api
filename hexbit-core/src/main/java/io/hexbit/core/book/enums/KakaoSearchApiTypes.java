package io.hexbit.core.book.enums;

import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
public enum KakaoSearchApiTypes {

    SEARCH_WEB      ("https://dapi.kakao.com/v2/search/web",      HttpMethod.GET, "웹 검색"),
    SEARCH_VCLIP    ("https://dapi.kakao.com/v2/search/vclip",    HttpMethod.GET, "동영상 검색"),
    SEARCH_IMAGE    ("https://dapi.kakao.com/v2/search/image",    HttpMethod.GET, "이미지 검색"),
    SEARCH_BLOG     ("https://dapi.kakao.com/v2/search/blog",     HttpMethod.GET, "블로그 검색"),
    SEARCH_BOOK     ("https://dapi.kakao.com/v3/search/book",     HttpMethod.GET, "책 검색"),
    SEARCH_CAFE     ("https://dapi.kakao.com/v2/search/cafe",     HttpMethod.GET, "카페 검색")
    ;

    private final String endPoint;
    private final HttpMethod methodType;
    private final String description;

    KakaoSearchApiTypes(String endPoint, HttpMethod methodType, String description) {
        this.endPoint = endPoint;
        this.methodType = methodType;
        this.description = description;
    }

}
