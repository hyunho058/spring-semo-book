package com.semobook.book.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("REDIS_BOOK")
public class RedisBook {
    @Id
    private String isbn;
    private String contents;
    private String bookName;
    private String author;
    private String publisher;
    private String kdc;
    private String category;
    private String keyword;
    private String img;

    @Builder
    public RedisBook(String isbn, String bookName, String author,String publisher,
                         String kdc, String category, String keyword, String img, String contents){
        this.isbn = isbn;
        this.bookName = bookName;
        this.publisher = publisher;
        this.author = author;
        this.kdc = kdc;
        this.category = category;
        this.keyword = keyword;
        this.img = img;
        this.contents = contents;
    }

}
