package com.semobook.bookReview.dto;

import com.semobook.book.domain.Book;
import com.semobook.book.dto.BookDto;
import com.semobook.bookReview.domain.BookReview;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookReviewWithIsbnDto {
    private long reviewNo;
    private int rating;
    private String reviewContents;
    private LocalDateTime createDate;
    private int declaration;
    private String userName;
//    private String isbn;
    private BookDto bookDto;

    public BookReviewWithIsbnDto(BookReview bookReview) {
        rating = bookReview.getRating();
        reviewContents = bookReview.getReviewContents();
        createDate = bookReview.getCreateDate();
        declaration = bookReview.getDeclaration();
        userName = bookReview.getUserInfo().getUserName();
        bookDto = new BookDto(bookReview.getBook());
    }
}
