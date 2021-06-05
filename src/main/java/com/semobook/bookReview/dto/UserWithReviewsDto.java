package com.semobook.bookReview.dto;

import com.semobook.user.domain.UserInfo;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserWithReviewsDto {
    private long userNo;
    private String userId;
    private List<BookReviewWithBookDto> bookReviewWithBookDtoList;

    public UserWithReviewsDto(UserInfo userInfo) {
        this.userNo = userInfo.getUserNo();
        this.userId = userInfo.getUserId();
        this.bookReviewWithBookDtoList = userInfo.getBookReviews().stream()
                .map(r->new BookReviewWithBookDto(r))
                .collect(Collectors.toList());
    }
}
