package io.hexbit.core.book.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Book {

    private String title;           // 도서 제목
    private String contents;        // 도서 소개
    private String url;             // 도서 상세 URL
    private String isbn;            // 국제 표준 도서번호, ISBN10 또는 ISBN13
    private LocalDateTime datetime; // 도서 출판 날짜, ISO 8601 형식
    private List<String> authors;     // 도서 저자 리스트
    private String publisher;         // 도서 출판사
    private List<String> translators; // 도서 번역자 리스트
    private int price;              // 도서 정가
    private int salePrice;          // 도서 판매가
    private String thumbnail;       // 도서 표지 썸네일 URL
    private String status;          // 도서 판매 상태 정보 (정상, 품절, 절판 등), 정상(default)

    @Builder
    public Book(String title, String contents, String url, String isbn, LocalDateTime datetime, List<String> authors, String publisher, List<String> translators, int price, int salePrice, String thumbnail, String status) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.isbn = isbn;
        this.datetime = datetime;
        this.authors = authors;
        this.publisher = publisher;
        this.translators = translators;
        this.price = price;
        this.salePrice = salePrice;
        this.thumbnail = thumbnail;
        this.status = status;
    }

    public static Book of(KakaoBookSearchResponse.Document document) {
        return Book.builder()
                .title(document.getTitle())
                .contents(document.getContents())
                .url(document.getUrl())
                .isbn(document.getIsbn())
                .datetime(document.getDatetime())
                .authors(document.getAuthors())
                .publisher(document.getPublisher())
                .translators(document.getTranslators())
                .price(document.getPrice())
                .salePrice(document.getSalePrice())
                .thumbnail(document.getThumbnail())
                .status(document.getStatus())
                .build();
    }
}
