package com.semobook.bookReview.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthBookReviewRequest {
    @Schema(description = "유저번호" , example = "1")
    long userNo;
    @Schema(description = "x월" , example = "1")
    int month;
}
