package com.semobook.recom.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


/**
* 유저가 종합적으로 준 평점 기반 추천 (배치)
 * RECOM_USER_TOTAL_12
* @author hyejinzz
* @since 2021-05-29
**/
@Getter
@RedisHash("RECOM_USER_TOTAL")
public class RecomUserTotal {
    @Id
    long userNo;
    private String isbn;
    private String bookName;
    private String author;
    private String publisher;
    private String kdc;
    private String category;
    private String keyword;
    private String img;

    @Builder
    public RecomUserTotal(long userNo, String isbn, String bookName, String author,String publisher,
                           String kdc, String category, String keyword, String img){
        this.userNo = userNo;
        this.isbn = isbn;
        this.bookName = bookName;
        this.publisher = publisher;
        this.author = author;
        this.kdc = kdc;
        this.category = category;
        this.keyword = keyword;
        this.img = img;
    }

}
