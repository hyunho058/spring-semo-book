package com.semobook.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class UserDeleteRequest {
    @Schema(description = "유저아이디" , example = "1")
    long userNo;
    @Schema(description = "삭제 사유" , example = "맘에안들어서요")
    String deleteReason;
}

