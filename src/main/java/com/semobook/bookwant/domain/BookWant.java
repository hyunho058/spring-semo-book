package com.semobook.bookwant.domain;

import com.semobook.book.domain.Book;
import com.semobook.book.dto.BookWithReviewDto;
import com.semobook.bookwant.dto.Preference;
import com.semobook.user.domain.UserInfo;
import com.semobook.user.dto.UserInfoDto;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn" )
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserInfo userInfo;



    @Builder
    public BookWant(Preference preference, BookWithReviewDto bookDto, UserInfoDto userInfo){
        this.preference = preference;
        this.book = Book.builder()
                .bookName(bookDto.getBookName())
                .isbn(bookDto.getIsbn())
                .author(bookDto.getAuthor())
                .publisher(bookDto.getPublisher())
                .kdc(bookDto.getKdc())
                .category(bookDto.getCategory())
                .keyword(bookDto.getKeyword())
                .img(bookDto.getImg())
                .build();
                // TODO: 2021-06-10 book 으로변환
        this.userInfo = UserInfo.builder()
                .userNo(userInfo.getUserNo())
        .build();
    }

}
