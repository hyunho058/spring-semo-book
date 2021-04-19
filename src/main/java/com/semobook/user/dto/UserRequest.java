package com.semobook.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    @Schema(description = "유저아이디" , example = "0")
    String userId;
    @Schema(description = "유저패스워드" , example = "0")
    String userPassword;
    @Schema(description = "유저이름" , example = "0")
    String userName;
    @Schema(description = "유저성별" , example = "W")
    String userGender;
    @Schema(description = "유저생일" , example = "940810")
    String userBirth;
//    String userStatus;

}
