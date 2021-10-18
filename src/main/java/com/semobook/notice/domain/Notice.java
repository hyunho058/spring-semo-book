package com.semobook.notice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Notice {
    /**
     * 공지사항  관리페이지
     *
     * @author juhan
     * @since 2021-07-17
    **/
    @Id
    @GeneratedValue
    private long noticeNo;

    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime createTime;

    @Builder
    public Notice(String noticeTitle, String noticeContent, LocalDateTime createTime){
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.createTime = createTime;
    }
    public void UpdateNotice(String noticeTitle, String noticeContent, LocalDateTime createTime){
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.createTime = createTime;
    }
}
