package com.semobook.recom.domain;

import com.sun.org.apache.xpath.internal.operations.Equals;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


/**
* 베스트셀러 추천 (배치)
 * RECOM_BEST_SELLER:A_1 : bestSellsr 종합 1위
* @author hyejinzz
* @since 2021-05-29
**/
@NoArgsConstructor
@Getter
@RedisHash("RECOM_BEST_SELLER")
public class RecomBestSeller  implements Serializable {
    @Id
    private String rank;
    private String isbn;
    private String bookName;
    private String author;
    private String publisher;
    private String kdc;
    private String category;
    private String keyword;
    private String img;


    @Builder
    public RecomBestSeller(String rank, String isbn, String bookName, String author,String publisher,
                           String kdc, String category, String keyword, String img){
        this.rank = rank;
        this.isbn = isbn;
        this.bookName = bookName;
        this.publisher = publisher;
        this.author = author;
        this.kdc = kdc;
        this.category = category;
        this.keyword = keyword;
        this.img = img;
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RecomBestSeller) {
            return isbn.equals(((RecomBestSeller) o).isbn);
        }
        return false;
    }


}
