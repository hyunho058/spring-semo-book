package com.semobook.bookwant.domain;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
*
* 이용자가 나중에 볼 책 /
* @author hjjung
* @since 2021-05-16
**/

@Entity
@Getter
@NoArgsConstructor
public class BookWant {

    @Id
    @GeneratedValue
    private int wantNo;
    private String preference;
    private String isbn;
    private int userNo;

    @Builder
    public BookWant(String preference, String isbn, int userNo){
        this.preference = preference;
        this.isbn = isbn;
        this.userNo = userNo;

    }

}
