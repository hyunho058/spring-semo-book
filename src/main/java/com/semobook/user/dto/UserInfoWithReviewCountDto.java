package com.semobook.user.dto;

import lombok.Data;

@Data
public class UserInfoWithReviewCountDto {
    private long userNo;
    private String userId;
    private String userName;
    private long reviewCount;

    public UserInfoWithReviewCountDto(long userNo, String userId, String userName, long reviewCount) {
        this.userNo = userNo;
        this.userId = userId;
        this.userName = userName;
        this.reviewCount = reviewCount;
    }
}
