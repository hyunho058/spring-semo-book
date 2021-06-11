package com.semobook.bookReview.dto;

import com.semobook.bookReview.domain.BookReview;
import com.semobook.user.domain.UserInfo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class BookReviewDto {
    private int rating;
    private String reviewContents;
    private LocalDateTime createDate;
    private int declaration;
    private long userNo;
    private String userName;

    public BookReviewDto(BookReview bookReview) {
        rating = bookReview.getRating();
        reviewContents = bookReview.getReviewContents();
        createDate = bookReview.getCreateDate();
        declaration = bookReview.getDeclaration();
        userNo = bookReview.getUserInfo().getUserNo();
        userName = bookReview.getUserInfo().getUserName();

//        isbn = bookReview.getBook().getIsbn();
    }
}
