package com.semobook.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    String userId;
    String userPassword;
    String userName;
    String userGender;
    String userBirth;
    String userStatus;

}
