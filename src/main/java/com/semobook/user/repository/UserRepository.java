package com.semobook.user.repository;


import com.semobook.user.domain.UserInfo;
import com.semobook.user.domain.UserStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {

    List<UserInfo> findAll();

    //회원 조회 (휴먼, 정지, 탈퇴 제외)
    UserInfo findByUserIdAndUserStatus(String userId, Enum<UserStatus> status);

   //회원조회(모두)
    UserInfo findByUserId(String userId);

//    //회원가입
    UserInfo save(UserInfo userInfo);
//
//    //회원상태 변경
//    User changeStatus(String userStatus);
//
//    //회원 탈퇴
//    boolean deleteByUserId(String userId);



}
