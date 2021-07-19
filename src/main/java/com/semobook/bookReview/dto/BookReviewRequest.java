package com.semobook.bookReview.dto;

import com.semobook.book.dto.BookDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class BookReviewRequest {
    @Schema(description = "유저no" , example = "12")
    long userNo;
    @Schema(description = "책isbn" , example = "9788998139766")
    String isbn;
    @Schema(description = "평점" , example = "3")
    int rating;
    @Schema(description = "리뷰내용" , example = "정말재밌는책이었다.")
    String reviewContents;
    @Column(columnDefinition = "책 정보")
    BookDto book;

    @Builder
    public BookReviewRequest(long userNo, String isbn, int rating, String reviewContents, BookDto book) {
        this.userNo = userNo;
        this.isbn = isbn;
        this.rating = rating;
        this.reviewContents = reviewContents;
        this.book = book;
    }
}
