package com.semobook.qa.dto;

import com.semobook.qa.domain.Qa;
import com.semobook.user.domain.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateQaDto {

    private String title;

    private String requestContents;

    private UserInfo userInfo;

    @Builder
    public CreateQaDto(Qa qa) {
        this.title = qa.getTitle();
        this.requestContents = qa.getRequestContents();
        this.userInfo = qa.getUserInfo();
    }
}
