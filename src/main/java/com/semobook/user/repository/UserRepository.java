package com.semobook.user.repository;


import com.semobook.user.domain.UserInfo;
import com.semobook.user.domain.UserStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {

    //모든 유저 찾기
    @Query("select b from UserInfo b join fetch b.bookReviews")
    List<UserInfo> findAll();

    //유저no로 회원 조회 (휴먼, 정지, 탈퇴 제외)
    UserInfo findByUserIdAndUserStatus(String userId, Enum<UserStatus> status);

    //유저id로 회원조회 찾기 (휴먼, 정지, 탈퇴 제외)
    UserInfo findByUserNoAndUserStatus(long userNo, Enum<UserStatus> status);

   //회원조회(모두)
    UserInfo findByUserId(String userId);

    /**
     * find user by userNo
     *
     * @author hyunho
     * @since 2021/05/30
    **/
    UserInfo findByUserNo(long userNo);

    //회원가입
    UserInfo save(UserInfo userInfo);



}
