package com.semobook.bookReview.dto.request;

import com.semobook.bookReview.domain.BookReview;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchBookReviewDto {
    private long reviewNo;
    private int rating;
    private String reviewContents;
    private LocalDateTime createDate;
    private int declaration;
    private String userName;

    public SearchBookReviewDto(BookReview bookReview) {
        reviewNo = bookReview.getReviewNo();
        rating = bookReview.getRating();
        reviewContents = bookReview.getReviewContents();
        createDate = bookReview.getCreateDate();
        declaration = bookReview.getDeclaration();
        userName = bookReview.getUserInfo().getUserName();
    }
}
