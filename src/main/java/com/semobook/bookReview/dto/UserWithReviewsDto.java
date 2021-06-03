package com.semobook.bookReview.dto;

import com.semobook.bookReview.domain.BookReview;
import com.semobook.user.domain.UserInfo;
import com.semobook.user.domain.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserWithReviewsDto {
    private long userNo;
    private String userId;
    private List<BookReviewDto> bookReviewDtoList;

    public UserWithReviewsDto(UserInfo userInfo) {
        this.userNo = userInfo.getUserNo();
        this.userId = userInfo.getUserId();
        this.bookReviewDtoList = userInfo.getBookReviews().stream()
                .map(r->new BookReviewDto(r))
                .collect(Collectors.toList());
    }
}
