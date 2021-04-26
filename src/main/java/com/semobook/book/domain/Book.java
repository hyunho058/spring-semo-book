package com.semobook.book.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "ISBN")
    private Long isbn;

    @Column(name = "BOOK_NAME")
    private String bookName;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "KDC")
    private String kdc;

    @Column(name = "CATEGORYy")
    private String category;

    @Column(name = "KEYWORD")
    private String keyword;

    @Column(name = "BOOK_IMAGE")
    private String img;

    @Builder
    public Book(Long isbn, String bookName, String author, String publisher, String kdc, String category, String keyword, String img) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.kdc = kdc;
        this.category = category;
        this.keyword = keyword;
        this.img = img;
    }
}
