package com.semobook.bookReview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookSearchRequest {
    @Schema(description = "유저no" , example = "12")
    long userNo;
    @Schema(description = "시작페이지, 시작페이지 기준으로 5개 나옴" , example = "1")
    int startPage;

}
