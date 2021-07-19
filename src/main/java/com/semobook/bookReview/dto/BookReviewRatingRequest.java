package com.semobook.bookReview.dto;


import com.semobook.book.dto.BookDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookReviewRatingRequest {
    @Schema(description = "유저no" , example = "12")
    long userNo;
    @Schema(description = "평점" , example = "3")
    int rating;
    @Schema(description = "book data")
    BookDto book;

    @Builder
    public BookReviewRatingRequest(long userNo, int rating, BookDto book){
        this.userNo = userNo;
        this.rating = rating;
        this.book = book;
    }
}
