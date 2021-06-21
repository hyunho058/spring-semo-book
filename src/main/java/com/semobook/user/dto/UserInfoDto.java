package com.semobook.user.dto;

import com.semobook.user.domain.UserInfo;
import com.semobook.user.domain.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoDto {
    private long userNo;
    private String userId;
    private String userPw;
    private UserStatus userStatus;
    private String userName;
    private String userGender;
    private String userBirth;
    private LocalDateTime lastConnection;
    private String userPriority;

    public UserInfoDto(UserInfo userInfo) {
        this.userNo = userInfo.getUserNo();
        this.userId = userInfo.getUserId();
        this.userPw = userInfo.getUserPw();
        this.userStatus = userInfo.getUserStatus();
        this.userName = userInfo.getUserName();
        this.userGender = userInfo.getUserGender();
        this.userBirth = userInfo.getUserBirth();
        this.lastConnection = userInfo.getLastConnection();
        this.userPriority = userInfo.getUserPriority();
    }
}
