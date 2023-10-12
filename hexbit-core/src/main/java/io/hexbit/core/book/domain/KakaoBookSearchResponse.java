package io.hexbit.core.book.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.hexbit.core.common.jackson.DefaultLocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

// https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-book-response
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoBookSearchResponse {

    private Meta meta;
    private List<Document> documents;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Meta {
        private int totalCount;     // 검색된 문서 수
        private int pageableCount;  // 중복된 문서를 제외하고, 처음부터 요청 페이지까지의 노출 가능 문서 수
        private boolean isEnd;      // 현재 페이지가 마지막 페이지인지 여부
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Document {
        private String title;           // 도서 제목
        private String contents;        // 도서 소개
        private String url;             // 도서 상세 URL
        private String isbn;

        @JsonDeserialize(using = DefaultLocalDateTimeDeserializer.class)
        private LocalDateTime datetime; // 도서 출판 날짜, ISO 8601 형식
        private List<String> authors;     // 도서 저자 리스트
        private String publisher;         // 도서 출판사
        private List<String> translators; // 도서 번역자 리스트
        private int price;              // 도서 정가
        private int salePrice;          // 도서 판매가
        private String thumbnail;       // 도서 표지 썸네일 URL
        private String status;          // 도서 판매 상태 정보 (정상, 품절, 절판 등), 정상(default)
    }

}

