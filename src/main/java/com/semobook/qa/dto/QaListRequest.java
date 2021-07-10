package com.semobook.qa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QaListRequest {
    @Schema(description = "userNo", example = "1")
    private long userNo;

    @Schema(description = "page", example = "0")
    private String pageNum;
}
