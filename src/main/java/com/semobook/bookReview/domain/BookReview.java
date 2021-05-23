package com.semobook.bookReview.domain;

import com.semobook.user.domain.UserInfo;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class BookReview {
    @Id
    @GeneratedValue
    private long reviewNo;
    private String isbn;

    private int rating;
    private String reviewContents;
    private LocalDateTime createDate;
    private int declaration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserInfo userInfo;

    protected BookReview(){

    }

    @Builder
    public BookReview(String isbn,  int rating,
                      String reviewContents, LocalDateTime createDate, int declaration){
     this.isbn = isbn;
     this.rating = rating;
     this.reviewContents = reviewContents;
     this.createDate = createDate;
     this.declaration = declaration;

    }

    public void changeBookReview(int rating,
                                 String reviewContents
    ){
        this.rating = rating;
        this.reviewContents = reviewContents;
    }

}
