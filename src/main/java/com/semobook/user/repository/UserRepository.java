package com.semobook.user.repository;


import com.semobook.user.domain.UserInfo;
import com.semobook.user.domain.UserStatus;
import com.semobook.user.dto.UserInfoDto;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {

    //모든 유저 찾기
    @Query(value = "select ui from UserInfo ui",
            countQuery = "select count(ui.userNo) from UserInfo  ui")
    Page<UserInfo> findAll(Pageable pageable);

    //유저no로 회원 조회 (휴먼, 정지, 탈퇴 제외)
//    @Query("select distinct u from UserInfo u join fetch u.bookReviews ur join fetch ur.userInfo")
//    UserInfo findByUserIdAndUserStatus(@Param("userId") String userId, Enum<UserStatus> status);
    UserInfo findByUserIdAndUserStatus( String userId, Enum<UserStatus> status);

    //유저id로 회원조회 찾기 (휴먼, 정지, 탈퇴 제외)
    UserInfo findByUserNoAndUserStatus(long userNo, Enum<UserStatus> status);

   //회원조회(모두)
    UserInfo findByUserId(String userId);

    //find user by userNo
    @Query("select u from UserInfo u left join fetch u.bookReviews br where u.userNo = :userNo")
    UserInfo findByUserNo(@Param("userNo") long userNo);

    @Query("select u from UserInfo u join fetch u.bookReviews br join fetch br.book")
    UserInfo findByUserNoWithReview(long userNo);


    //유저별 원하는 책 가져오기
    @Query("select u from UserInfo u join fetch u.bookWants bw join fetch bw.book where u.userNo = :userNo")
    UserInfo findByBookWantWithReview(@Param("userNo") long userNo);



    //회원가입
    UserInfo save(UserInfo userInfo);



}
