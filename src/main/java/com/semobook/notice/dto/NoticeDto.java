package com.semobook.notice.dto;


import com.semobook.notice.domain.Notice;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NoticeDto {

    private long noticeNo;

    private String noticeTitle;

    private String noticeContent;

    private LocalDateTime createTime;

    public NoticeDto(Notice notice){
        this.noticeNo = notice.getNoticeNo();
        this.noticeTitle = notice.getNoticeTitle();
        this.noticeContent = notice.getNoticeContent();
        this.createTime = notice.getCreateTime();
    }
}
