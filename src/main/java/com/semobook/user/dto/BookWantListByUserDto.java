package com.semobook.user.dto;

import com.semobook.bookwant.dto.BookWantDto;
import com.semobook.user.domain.UserInfo;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


/**
* 유저별 유저가 다음에 읽고 싶은 책 / 더이상 추천받고 싶지 않은 책 가져오기
* @author hyejinzz
* @since 2021-06-13
**/
@Data
public class BookWantListByUserDto {
    private long userNo;
    private String userId;
    private String userName;
    private List<BookWantDto> bookWants;

    public BookWantListByUserDto(UserInfo userInfo){
        this.userNo = userInfo.getUserNo();
        this.userId = userInfo.getUserId();
        this.userName = userInfo.getUserName();
        this.bookWants = userInfo.getBookWants().stream()
                .map(bookWant -> new BookWantDto(bookWant))
                .collect(Collectors.toList());
    }


}
