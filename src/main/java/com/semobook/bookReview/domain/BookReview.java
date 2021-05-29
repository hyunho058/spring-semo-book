package com.semobook.bookReview.domain;

import com.semobook.book.domain.Book;
import com.semobook.user.domain.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class BookReview {
    @Id
    @GeneratedValue
    private long reviewNo;

    private int rating;
    private String reviewContents;
    private LocalDateTime createDate;
    private int declaration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "isbn")
    private Book book;


    @Builder
    public BookReview(Book book, int rating, String reviewContents, LocalDateTime createDate, int declaration) {
        this.book = book;
        this.rating = rating;
        this.reviewContents = reviewContents;
        this.createDate = createDate;
        this.declaration = declaration;

    }

    public void changeBookReview(int rating,
                                 String reviewContents
    ) {
        this.rating = rating;
        this.reviewContents = reviewContents;
    }

}
