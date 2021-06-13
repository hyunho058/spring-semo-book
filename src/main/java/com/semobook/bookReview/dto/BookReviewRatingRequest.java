package com.semobook.bookReview.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookReviewRatingRequest {
    @Schema(description = "유저no" , example = "12")
    long userNo;
    @Schema(description = "책isbn" , example = "9788998139766")
    String isbn;
    @Schema(description = "평점" , example = "3")
    int rating;
}
