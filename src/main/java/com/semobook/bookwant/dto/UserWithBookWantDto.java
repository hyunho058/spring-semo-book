package com.semobook.bookwant.dto;

import com.semobook.bookReview.dto.BookReviewWithBookDto;
import com.semobook.user.domain.UserInfo;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserWithBookWantDto {
    private long userNo;
    private String userId;
    private List<BookWantDto> bookReviewWithBookDtoList;

    public UserWithBookWantDto(UserInfo userInfo) {
        this.userNo = userInfo.getUserNo();
        this.userId = userInfo.getUserId();
        this.bookReviewWithBookDtoList = userInfo.getBookWants().stream()
                .map(r->new BookWantDto(r))
                .collect(Collectors.toList());

    }
}
