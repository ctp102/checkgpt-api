package io.hexbit.core.book.service;

import io.hexbit.core.book.domain.Book;
import io.hexbit.core.book.domain.KakaoBookSearchResponse;
import io.hexbit.core.book.form.KakaoBookForm;
import io.hexbit.core.oauth2.domain.KakaoResponse;
import io.hexbit.core.oauth2.restclient.KakaoRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final KakaoRestClient kakaoRestClient;

    @Transactional
    public Page<Book> searchBook(KakaoBookForm kakaoBookForm, Pageable pageable) {

        KakaoResponse<KakaoBookSearchResponse> kakaoBookSearchResponse = kakaoRestClient.getKakaoSearchBook(kakaoBookForm, pageable);

        KakaoBookSearchResponse kakaoBookSearchResponseData = kakaoBookSearchResponse.getData();

        if (kakaoBookSearchResponseData == null) {
            log.info("[searchBook] kakaoBookSearchResponseData Error: {}", kakaoBookSearchResponse.getError());
            return null;
        }

        List<Book> books = new ArrayList<>();
        kakaoBookSearchResponseData.getDocuments().forEach(document -> {
            books.add(Book.of(document));
        });

        return new PageImpl<>(books, pageable, kakaoBookSearchResponseData.getMeta().getTotalCount());
    }

}
