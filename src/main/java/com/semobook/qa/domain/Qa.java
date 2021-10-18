package com.semobook.qa.domain;

import com.semobook.user.domain.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Qa {
    @Id
    @GeneratedValue
    private long qaNo;

    private String title;

    private String requestContents;

    private String answerContents;

    private LocalDateTime createDate;

    @ColumnDefault("false")
    private String answerStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserInfo userInfo;

    @Builder
    public Qa(long qaNo, String title, String requestContents, String answerContents, LocalDateTime createDate, String answerStatus, UserInfo userInfo) {
        this.qaNo = qaNo;
        this.title = title;
        this.requestContents = requestContents;
        this.answerContents = answerContents;
        this.createDate = createDate;
        this.answerStatus = answerStatus;
        this.userInfo = userInfo;
    }
}
