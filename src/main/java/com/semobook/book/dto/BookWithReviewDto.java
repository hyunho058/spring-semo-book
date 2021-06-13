package com.semobook.book.dto;

import com.semobook.book.domain.Book;
import com.semobook.bookReview.dto.BookReviewWithUserNameDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookWithReviewDto {

    private String isbn;
    private String bookName;
    private String author;
    private String publisher;
    private String kdc;
    private String category;
    private String keyword;
    private String img;
    private List<BookReviewWithUserNameDto> bookReviews;

    public BookWithReviewDto(Book book) {
        this.isbn = book.getIsbn();
        this.bookName = book.getBookName();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.kdc = book.getKdc();
        this.category = book.getCategory();
        this.keyword = book.getKeyword();
        this.img = book.getImg();
        this.bookReviews = book.getBookReviewList().stream()
                .map(bookReview -> new BookReviewWithUserNameDto(bookReview))
                .collect(Collectors.toList());
    }
}
