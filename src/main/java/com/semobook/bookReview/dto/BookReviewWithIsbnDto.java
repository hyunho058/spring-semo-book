package com.semobook.bookReview.dto;

import com.semobook.bookReview.domain.BookReview;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookReviewWithIsbnDto {
    private int rating;
    private String reviewContents;
    private LocalDateTime createDate;
    private int declaration;
    private long userNo;
    private String userName;
    private String isbn;

    public BookReviewWithIsbnDto(BookReview bookReview) {
        rating = bookReview.getRating();
        reviewContents = bookReview.getReviewContents();
        createDate = bookReview.getCreateDate();
        declaration = bookReview.getDeclaration();
        userNo = bookReview.getUserInfo().getUserNo();
        userName = bookReview.getUserInfo().getUserName();
        isbn = bookReview.getBook().getIsbn();
    }
}
