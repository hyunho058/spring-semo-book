package com.semobook.book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.semobook.book.dto.BookDto;
import com.semobook.bookReview.domain.BookReview;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "BOOK_NAME")
    private String bookName;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "KDC")
    private String kdc;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "KEYWORD")
    private String keyword;

    @Column(name = "BOOK_IMAGE")
    private String img;

//    @JsonIgnore //bookReviewList 를 호회하지 않는다..... 쓰면 좋지 않다..... 쓰지마라
    @OneToMany(mappedBy = "book")
    private List<BookReview> bookReviewList = new ArrayList<>();

    @Builder
    public Book(String isbn, String bookName, String author, String publisher, String kdc, String category, String keyword, String img) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.kdc = kdc;
        this.category = category;
        this.keyword = keyword;
        this.img = img;
    }
    @Builder
    public Book(BookDto bookDto) {
        this.isbn = bookDto.getIsbn();
        this.bookName = bookDto.getBookName();
        this.author = bookDto.getAuthor();
        this.publisher = bookDto.getPublisher();
        this.kdc = bookDto.getKdc();
        this.category = bookDto.getCategory();
        this.keyword = bookDto.getKeyword();
        this.img = bookDto.getImg();
    }
}
