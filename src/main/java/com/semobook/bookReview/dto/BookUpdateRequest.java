package com.semobook.bookReview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookUpdateRequest {
    @Schema(description = "리뷰번호" , example = "1")
    long reviewNo;
    @Schema(description = "평점" , example = "3")
    int rating;
    @Schema(description = "리뷰내용" , example = "정말재밌는책이었다.")
    String reviewContents;
}
