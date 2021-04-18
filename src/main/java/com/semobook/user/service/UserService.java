package com.semobook.user.service;


import com.semobook.common.StatusEnum;
import com.semobook.user.domain.User;
import com.semobook.user.domain.UserStatus;
import com.semobook.user.dto.UserRequest;
import com.semobook.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserService {
    String hMessage;
    StatusEnum hCode;
    Object data;

    private final UserRepository userRepository;

    /**
     * 회원 조회
     *
     * @param userId
     * @return
     */
    public UserResponse findByUserId(String userId) {

        User user = userRepository.findByUserIdAndUserStatus(userId, UserStatus.GENERAL);
        if (user == null) {
            hCode = StatusEnum.hd4444;
            hMessage = "유효하지 않은 회원정보";
        } else {
            hCode = StatusEnum.hd1004;
            hMessage = userId;
            data = user;
        }

        return UserResponse.builder()
                .data(data)
                .hcode(hCode)
                .hMessage(hMessage)
                .build();
    }


    //    //로그인
    public UserResponse signIn(UserRequest userRequest) {
        log.info(":::로그인:::");
        User signUser = userRepository.findByUserIdAndUserStatus(userRequest.getUserId(), UserStatus.GENERAL);
        if (signUser == null) {
            hCode = StatusEnum.hd4444;
            hMessage = "없는 USER";
        }
        //id가 있을 때

        else if (!(userRequest.getUserPassword().equals(signUser.getUserPassword()))) {
            hCode = StatusEnum.hd4444;
            hMessage = "로그인 실패";
            data = null;
        } else {
            hCode = StatusEnum.hd1004;
            hMessage = "로그인 성공";
        }

        return UserResponse.builder()
                .hcode(hCode)
                .hMessage(hMessage)
                .data(data)
                .build();
    }

    //    //회원가입

    public UserResponse signUp(UserRequest userRequest) {

        User signUser = userRepository.findByUserId(userRequest.getUserId());
        if (signUser == null) {
            User user = userRepository.save(User.builder()
                    .userId(userRequest.getUserId())
                    .userPassword(userRequest.getUserPassword())
                    .userName(userRequest.getUserName())
                    .userGender(userRequest.getUserGender())
                    .userBirth(userRequest.getUserBirth())
                    .build());

            hMessage = "생성완료";
            hCode = StatusEnum.hd1004;
            data = user;
        }
        else{
            hMessage = "이미 있는 아이디";
            hCode = StatusEnum.hd4444;
            data = null;
        }

        return UserResponse.builder()
                .hcode(hCode)
                .hMessage(hMessage)
                .data(data)
                .build();

    }

    //회원탈퇴, 회원 상태 변경
//    public UserResponse deleteUser(UserRequest userRequest) {
//        userRepository.deleteByUserId(userRequest.getUserId());
//        hCode = StatusEnum.hd1004;
//        hMessage = "탈퇴 성공";
//
//        return UserResponse.builder()
//                .hcode(hCode)
//                .hMessage(hMessage)
//                .data(data)
//                .build();
//    }
//
//    //회원수정
//
//


}
