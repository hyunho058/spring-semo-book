package com.semobook.qa.dto;

import com.semobook.qa.domain.Qa;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class QaListDto {

    private long qaNo;

    private String title;

    private String requestContents;

    private String answerContents;

    private LocalDateTime createDate;

    private String answerStatus;

    private long userNo;


    public QaListDto(Qa qa) {
        this.qaNo = qa.getQaNo();
        this.title = qa.getTitle();
        this.requestContents = qa.getRequestContents();
        this.answerContents = qa.getAnswerContents();
        this.createDate = qa.getCreateDate();
        this.answerStatus = qa.getAnswerStatus();
        this.userNo = qa.getUserInfo().getUserNo();
    }
}
