package com.semobook.qa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QaRequest {
    @Schema(description = "유저no" , example = "12")
    private long userNo;

    @Schema(description = "제목" , example = "문의")
    private String title;

    @Schema(description = "내용" , example = "문의드립니다.")
    private String requestContents;

}
