package com.semobook.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoRequest {
    @Schema(description = "유저No" , example = "1")
    long userNo;
}
