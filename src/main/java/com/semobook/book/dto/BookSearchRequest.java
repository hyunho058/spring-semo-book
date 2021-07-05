package com.semobook.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BookSearchRequest {
    @Schema(description = "검색어" , example = "해리포터")
    String keyword;
    @Schema(description = "페이지", example = "1")
    int pageNum;

    @Builder
    public BookSearchRequest(String keyword, int pageNum){
        this.keyword = keyword;
        this.pageNum = pageNum;
    }
}
