package io.hexbit.api.controller;

import io.hexbit.api.security.annotation.ClientRequest;
import io.hexbit.api.security.annotation.Secured;
import io.hexbit.api.security.resolver.WebRequest;
import io.hexbit.core.book.domain.Book;
import io.hexbit.core.book.form.KakaoBookForm;
import io.hexbit.core.book.service.BookService;
import io.hexbit.core.common.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @Secured
    @GetMapping("/api/v1/search/book")
    public CustomResponse searchBook(
            @ClientRequest WebRequest webRequest,
            @ModelAttribute KakaoBookForm kakaoBookForm,
            @PageableDefault Pageable pageable) {

        // TODO: [2023/10/12, jh.cho] 로그인을 한 사용자만? 정책 정하자.

        Page<Book> items = bookService.searchBook(kakaoBookForm, pageable);

        return new CustomResponse.Builder().addItems(items).build();
    }

}
