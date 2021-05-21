package com.semobook.bookReview.domain;

import com.semobook.user.domain.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.sound.midi.Patch;
import java.time.LocalDateTime;

@Getter
@Entity
public class BookReview {
    @Id
    @GeneratedValue
    private long reviewNo;
    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="userNo")
    private UserInfo userNo;

    private int rating;
    private String reviewContents;
    private LocalDateTime createDate;
    private int declaration;

    @Builder
    public BookReview(String isbn, UserInfo userNo, int rating,
                      String reviewContents, LocalDateTime createDate, int declaration){
     this.isbn = isbn;
     this.userNo = userNo;
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
