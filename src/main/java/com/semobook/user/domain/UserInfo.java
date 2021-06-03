package com.semobook.user.domain;

import com.semobook.bookReview.domain.BookReview;
import com.semobook.user.dto.UserChangeUserInfoRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class UserInfo {


    @Id
    @GeneratedValue
    private long userNo;

    private String userId;

    private String userPw;
    //GENERAL(일반),PAUSE(정지),SLEEP(휴먼),DELETE(삭제),ADMIN(관리자)',
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private String userName;

    private String userGender;

    //'성별, W : 여자 /  M : 남자',
    private String userBirth;

    private LocalDateTime lastConnection;

    @OneToMany(mappedBy = "userInfo")
    private List<BookReview> bookReviews = new ArrayList<>();

    private String deleteReason;

    private LocalDateTime delDate;


    @Builder
    public UserInfo(String userId, String userPw, String userName, String userGender, String userBirth, LocalDateTime lastConnection) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userGender = userGender;
        this.userBirth = userBirth;
        this.userStatus = UserStatus.GENERAL;
        this.lastConnection = lastConnection;
    }

    public void changeUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void delUser(String deleteReason, LocalDateTime delDate) {
        this.userStatus = UserStatus.DELETE;
        this.deleteReason = deleteReason;
        this.delDate = delDate;
    }

    public void changeUserInfo(UserChangeUserInfoRequest userInfo) {
        this.userPw = userInfo.getUserPassword();
        this.userName = userInfo.getUserName();
        this.userGender = userInfo.getUserGender();
        this.userBirth = userInfo.getUserBirth();
    }


}