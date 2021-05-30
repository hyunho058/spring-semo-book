package com.semobook.bookReview.domain;

import com.semobook.book.domain.Book;
import com.semobook.user.domain.UserInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookReviewDto {
    private int rating;
    private String reviewContents;
    private LocalDateTime createDate;
    private int declaration;
    private Book book;
    private UserInfo userInfo;

    public BookReviewDto(BookReview bookReview) {
        rating = bookReview.getRating();
        reviewContents = bookReview.getReviewContents();
        createDate = bookReview.getCreateDate();
        declaration = bookReview.getDeclaration();
        book = bookReview.getBook();
        userInfo = bookReview.getUserInfo();
    }
}