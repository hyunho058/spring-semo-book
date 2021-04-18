package com.semobook.user.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
@Table(name = "user_info")
public class User {


    @Id
    @GeneratedValue
    @Column(name = "NO")
    private int no;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PW")
    private String userPassword;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_ROLE")
    private int userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS")
    private UserStatus userStatus;

    @Column(name = "USER_GENDER")
    private String userGender;

    @Column(name = "USER_BIRTH")
    private String userBirth;


    @Builder
    public User(String userId, String userPassword, String userName, int userRole, String userGender, String userBirth) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userRole = userRole;
        this.userGender = userGender;
        this.userBirth = userBirth;
        this.userStatus = UserStatus.GENERAL;
    }

}