package io.hexbit.core.book.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class KakaoBookForm {

    @Schema(description = "검색어", example = "토비의 스프링")
    private String query;

    @Schema(description = "결과 정렬 방식", example = "accuracy", allowableValues = {"accuracy", "latest"}, defaultValue = "accuracy")
    private String sort = "accuracy";   // accuracy(default), latest

    @Schema(description = "검색 필드", example = "title", allowableValues = {"title", "isbn", "publisher", "person"}, defaultValue = "title")
    private String target; // title, isbn, publisher, person

    public KakaoBookForm() {
    }

    @Builder
    public KakaoBookForm(String query, String sort, String target) {
        this.query = query;
        this.sort = sort;
        this.target = target;
    }

}
