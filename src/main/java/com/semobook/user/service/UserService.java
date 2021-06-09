package com.semobook.user.service;


import com.semobook.common.StatusEnum;
import com.semobook.user.domain.UserInfo;
import com.semobook.user.domain.UserStatus;
import com.semobook.user.dto.*;
import com.semobook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserService {


    private final UserRepository userRepository;

    /**
     * 모든회원 조회(테스트용)
     *
     * @author hyejinzz
     * @since 2021-05-23
     **/
    public UserResponse findAllUser() {
        String hMessage = "";
        StatusEnum hCode = null;
        Object data = null;
        try {
            //TODO[hyunho]: cascade 적용 또는 페이지 처리 해야함. => 현재 조회를 하면 리스트가 중복되서 보여짐(@oneToMany를 호출시 흔히 발생하는 현상)
            List<UserInfo> list = userRepository.findAll();
            List<UserInfoDto> result = list.stream()
                    .map(r -> new UserInfoDto(r))
                    .collect(Collectors.toList());
            data = result;
        } catch (Exception e) {
            hCode = StatusEnum.hd4444;
            hMessage = "회원조회 실패";
            log.info(":: findAllUser err :: error is {} ", e);
        }
        return UserResponse.builder()
                .data(data)
                .hcode(hCode)
                .hMessage(hMessage)
                .build();
    }


    /**
     * userId로 회원조회
     *
     * @author hyejinzz
     * @since 2021-05-23
     **/
    public UserResponse findByUserId(String userId) {
        String hMessage = "";
        StatusEnum hCode = null;
        Object data = null;

        try {
            UserInfoDto userInfoDto = new UserInfoDto(userRepository.findByUserIdAndUserStatus(userId, UserStatus.GENERAL));
            if (userInfoDto == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "유효하지 않은 회원정보";
            } else {
                hCode = StatusEnum.hd1004;
                hMessage = userId;
                data = userInfoDto;
            }
        } catch (Exception e) {
            hCode = StatusEnum.hd4444;
            hMessage = "회원조회 실패";
            log.info(":: findByUserId err :: error is {} ", e);
        }

        return UserResponse.builder()
                .data(data)
                .hcode(hCode)
                .hMessage(hMessage)
                .build();
    }


    /**
     * 로그인
     *
     * @author hyejinzz
     * @since 2021-05-23
     **/
    public UserResponse signIn(UserSignInRequest userSignUpRequest) {
        String hMessage = "";
        StatusEnum hCode = null;
        Object data = null;
        try {
            UserInfo signUserInfo = userRepository.findByUserIdAndUserStatus(userSignUpRequest.getUserId(), UserStatus.GENERAL);
            UserInfoDto userInfoDto = new UserInfoDto(signUserInfo);
            if (userInfoDto == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "없는 USER";
            }
            //id가 있을 때

            else if (!(userSignUpRequest.getUserPassword().equals(userInfoDto.getUserPw()))) {
                hCode = StatusEnum.hd4444;
                hMessage = "로그인 실패";
                data = null;
            } else {
                hCode = StatusEnum.hd1004;
                data = userInfoDto;
                hMessage = "로그인 성공";
            }
        } catch (Exception e) {
            hCode = StatusEnum.hd4444;
            hMessage = "로그인 실패";
            log.info(":: signIn err :: error is {} ", e);
        }

        return UserResponse.builder()
                .hcode(hCode)
                .hMessage(hMessage)
                .data(data)
                .build();
    }


    /**
     * 회원가입
     *
     * @author hyejinzz
     * @since 2021-05-23
     **/
    public UserResponse signUp(UserSignUpRequest userSignUpRequest) {
        String hMessage = "";
        StatusEnum hCode = null;
        Object data = null;
        UserInfo signUserInfo = userRepository.findByUserId(userSignUpRequest.getUserId());
        if (signUserInfo == null) {
            UserInfo userInfo = userRepository.save(UserInfo.builder()
                    .userId(userSignUpRequest.getUserId())
                    .userPw(userSignUpRequest.getUserPassword())
                    .userName(userSignUpRequest.getUserName())
                    .userGender(userSignUpRequest.getUserGender())
                    .userBirth(userSignUpRequest.getUserBirth())
                    .build());

            hMessage = "생성완료";
            hCode = StatusEnum.hd1004;
            data = userInfo;
        } else {
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


    /**
     * 회원탈퇴
     *
     * @author hyejinzz
     * @since 2021-05-23
     **/
    public UserResponse deleteUser(UserDeleteRequest userDeleteRequest) {
        String hMessage = "";
        StatusEnum hCode = null;
        Object data = null;
        try {
            //기존 유저
            UserInfo user = userRepository.findByUserNoAndUserStatus(userDeleteRequest.getUserNo(), UserStatus.GENERAL);

            if (user == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "조회되는 회원이 없음";
            } else {
                user.delUser(userDeleteRequest.getDeleteReason(), LocalDateTime.now());
                userRepository.save(user);
                hCode = StatusEnum.hd1004;
                data = user;
                hMessage = "탈퇴 성공";
            }

        } catch (Exception e) {
            log.info(":: deleteUser err :: error is {} ", e);
            hCode = StatusEnum.hd4444;
            hMessage = "탈퇴 실패";
        }
        return UserResponse.builder()
                .hcode(hCode)
                .hMessage(hMessage)
                .data(data)
                .build();
    }


    /**
     * 회원정보 수정
     *
     * @author hyejinzz
     * @since 2021-05-23
     **/
    public UserResponse updateUser(UserChangeUserInfoRequest updateUser) {
        String hMessage = "";
        StatusEnum hCode = null;
        Object data = null;
        try {
            //기존 유저
            UserInfo beforeUser = userRepository.findByUserNoAndUserStatus(updateUser.getUserNo(), UserStatus.GENERAL);

            if (beforeUser == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "조회되는 회원이 없음";
            } else {
                beforeUser.changeUserInfo(updateUser);
                userRepository.save(beforeUser);
                hCode = StatusEnum.hd1004;
                data = beforeUser;
                hMessage = "정보 수정 성공";
            }

        } catch (Exception e) {
            log.info(":: updateUser err :: error is {} ", e);
            hCode = StatusEnum.hd4444;
            hMessage = "정보 수정 실패";
        }
        return UserResponse.builder()
                .hcode(hCode)
                .hMessage(hMessage)
                .data(data)
                .build();
    }


}
