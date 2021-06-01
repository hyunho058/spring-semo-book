package com.semobook.book.dto;

import com.semobook.book.domain.Book;
import lombok.Data;

@Data
public class BookListDto {

    private String isbn;
    private String bookName;
    private String author;
    private String publisher;
    private String kdc;
    private String category;
    private String keyword;
    private String img;

    public BookListDto(Book book) {
        this.isbn = book.getIsbn();
        this.bookName = book.getBookName();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.kdc = book.getKdc();
        this.category = book.getCategory();
        this.keyword = book.getKeyword();
        this.img = book.getImg();
    }
}
