package com.semobook.user.repository;


import com.semobook.user.domain.User;
import com.semobook.user.domain.UserStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    //회원 조회 (휴먼, 정지, 탈퇴 제외)
    User findByUserIdAndUserStatus(String userId, Enum<UserStatus> status);

   //회원조회(모두)
    User findByUserId(String userId);

//    //회원가입
    User save(User user);
//
//    //회원상태 변경
//    User changeStatus(String userStatus);
//
//    //회원 탈퇴
//    boolean deleteByUserId(String userId);



}
