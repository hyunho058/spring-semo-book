package com.semobook.bookReview.dto;

import com.semobook.book.domain.Book;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.user.domain.UserInfo;
import com.semobook.user.dto.UserInfoDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookReviewWithBookDto {
    private int rating;
    private String reviewContents;
    private LocalDateTime createDate;
    private int declaration;
    private String isbn;

    public BookReviewWithBookDto(BookReview bookReview) {
        rating = bookReview.getRating();
        reviewContents = bookReview.getReviewContents();
        createDate = bookReview.getCreateDate();
        declaration = bookReview.getDeclaration();
        isbn = bookReview.getBook().getIsbn();
//        userName = bookReview.getUserInfo().getUserName();
    }
}