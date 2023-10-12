package io.hexbit.core.book.form;

import lombok.Data;

@Data
public class KakaoBookForm {

    private String query;
    private String sort;   // accuracy(default), latest
    private String target; // title, isbn, publisher, person

}
