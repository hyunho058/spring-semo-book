package com.semobook.bookwant.domain;

import com.semobook.book.domain.Book;
import com.semobook.bookwant.dto.Preference;
import com.semobook.user.domain.UserInfo;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

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
    private long wantNo;
    //LIKE,DISLIKE
    @Enumerated(EnumType.STRING)
    private Preference preference;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "isbn" )
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_no")
    private UserInfo userInfo;

    @Builder
    public BookWant(Preference preference, Book book, UserInfo userInfo){
        this.preference = preference;
        this.book = book;
        this.userInfo = userInfo;
    }

}
