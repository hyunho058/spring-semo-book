package com.semobook.bookReview.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteBookReviewRequest {
    @Schema(description = "review_no", example = "9999")
    long reviewNo;
}
