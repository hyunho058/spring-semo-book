package com.semobook.recom.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


/**
* 유저정보 (나이, 성별) 기반 추천 (배치)
 *  RECOM_USER_INFO_M_10 : 10대남성..
* @author hyejinzz
* @since 2021-05-29
**/
@Getter
@RedisHash("RECOM_USER_INFO")
public class RecomUserInfo {
    @Id
    private String info;
    private String isbn;
    private String bookName;
    private String author;
    private String publisher;
    private String kdc;
    private String category;
    private String keyword;
    private String img;

    @Builder
    public RecomUserInfo(String info, String isbn, String bookName, String author,String publisher,
                           String kdc, String category, String keyword, String img){
        this.info = info;
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
