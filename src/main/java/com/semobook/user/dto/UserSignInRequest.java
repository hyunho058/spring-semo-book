package com.semobook.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
public class UserSignInRequest {
    @Schema(description = "유저아이디" , example = "0")
    String userId;
    @Schema(description = "유저패스워드" , example = "0")
    String userPassword;
    @Schema(hidden = true)
    String userStatus;

}
