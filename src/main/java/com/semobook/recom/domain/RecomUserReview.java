package com.semobook.recom.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

/**
* 유저가 쓴 리뷰와 관련된 장르의 책을 추천해준다
 * RECOM_USER_REVIEW_12
* @author hyejinzz
* @since 2021-05-29
**/
@Getter
@RedisHash("RECOM_USER_REVIEW")
public class RecomUserReview {
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
    public RecomUserReview(long userNo, String isbn, String bookName, String author,String publisher,
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
