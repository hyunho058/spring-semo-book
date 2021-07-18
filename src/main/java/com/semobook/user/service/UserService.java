package com.semobook.user.service;


import com.semobook.bookReview.domain.AllReview;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.bookReview.repository.AllReviewRepository;
import com.semobook.bookReview.repository.BookReviewRepository;
import com.semobook.common.SemoConstant;
import com.semobook.common.StatusEnum;
import com.semobook.recom.domain.ReviewInfo;
import com.semobook.user.domain.UserPriorityRedis;
import com.semobook.bookReview.repository.AllReviewRepository;
import com.semobook.user.repository.UserPriorityRedisRepository;
import com.semobook.user.domain.UserInfo;
import com.semobook.user.domain.UserStatus;
import com.semobook.user.dto.*;
import com.semobook.user.repository.UserPriorityRedisRepository;
import com.semobook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserService {


    private final UserRepository userRepository;
    private final BookReviewRepository bookReviewRepository;
    private final UserPriorityRedisRepository userPriorityRedisRepository;
    private final AllReviewRepository allReviewRepository;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 모든회원 조회(테스트용)
     *
     * @author hyejinzz
     * @since 2021-05-23
     **/
    public UserResponse findAllUser(int pageNum) {
        String hMessage = "";
        StatusEnum hCode = null;
        Object data = null;
        try {
            //TODO[hyunho]: cascade 적용 또는 페이지 처리 해야함. => 현재 조회를 하면 리스트가 중복되서 보여짐(@oneToMany를 호출시 흔히 발생하는 현상)
            Page<UserInfo> page = userRepository.findAll(PageRequest.of(pageNum, 5));
            List<UserInfoDto> result = page.getContent().stream()
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
                .code(hCode)
                .message(hMessage)
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
//            UserInfoDto userInfoDto = new UserInfoDto(userRepository.findByUserIdAndUserStatus(userId, UserStatus.GENERAL));
            UserInfoDto userInfoDto = new UserInfoDto(userRepository.findByUserId(userId));
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
                .code(hCode)
                .message(hMessage)
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
//            UserInfo signUserInfo = userRepository.findByUserIdAndUserStatus(userSignUpRequest.getUserId(), UserStatus.GENERAL);
            UserInfo signUserInfo = userRepository.findByUserId(userSignUpRequest.getUserId());
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
                .code(hCode)
                .message(hMessage)
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
                .code(hCode)
                .message(hMessage)
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
                .code(hCode)
                .message(hMessage)
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
            UserInfo userInfo = userRepository.findByUserNoAndUserStatus(updateUser.getUserNo(), UserStatus.GENERAL);
            if (userInfo == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "조회되는 회원이 없음";
            } else {
                userInfo.changeUserInfo(updateUser);
                userRepository.save(userInfo);
                UserInfoDto userInfoDto = new UserInfoDto(userInfo);

                hCode = StatusEnum.hd1004;
                data = userInfoDto;
                hMessage = "정보 수정 성공";
            }

        } catch (Exception e) {
            log.info(":: updateUser err :: error is {} ", e);
            hCode = StatusEnum.hd4444;
            hMessage = "정보 수정 실패";
        }
        return UserResponse.builder()
                .code(hCode)
                .message(hMessage)
                .data(data)
                .build();
    }


    /**
     * user info with review count api
     *
     * @author hyunho
     * @since 2021/06/24
    **/
    public UserResponse userInfoWithReviewCount(UserInfoRequest userInfoRequest) {
        String hMessage = "";
        StatusEnum hCode = null;
        Object data = null;

        try {
            //TODO[hyunho] : 유저가 작성한 리뷰 수 조회 쿼리 수정 필요(oneToNmay 관계 collection 조회 필요)
            UserInfo userInfo = userRepository.findByUserNo(userInfoRequest.getUserNo());
            Page<BookReview> page = bookReviewRepository.findAllByUserInfo_userNo(userInfoRequest.getUserNo(), PageRequest.of(0, 100));
            UserInfoWithReviewCountDto userInfoWithReviewCountDto = new UserInfoWithReviewCountDto(userInfo.getUserNo(),
                    userInfo.getUserId(),
                    userInfo.getUserName(),
                    page.getTotalElements());

            hCode = StatusEnum.hd1004;
            data = userInfoWithReviewCountDto;
            hMessage = "정보 조회 성공";
        }catch (Exception e){
            log.info(":: userInfo err :: error is {} ", e);
            hCode = StatusEnum.hd4444;
            hMessage = "정보 조회 실패";
        }
        return UserResponse.builder()
                .code(hCode)
                .message(hMessage)
                .data(data)
                .build();
    }


    /**
     * 유저 성향 가져오기 리뷰데이터->redis -> db
     *
     * @author hyejinzz
     * @since 2021-06-20
     **/
    public List<String> getUserPriorityList(long userId) {
        List<String> userPriority = new ArrayList<>();
        //리뷰데이터로 새로운 성향 만들기
        makeUserPriority(userId);
        //redis
        String value;
        UserPriorityRedis userPriorityRedis = userPriorityRedisRepository.findById(userId).orElse(new UserPriorityRedis());
        //Database
        if (userPriorityRedis.getValue() == null) {
            UserInfoDto userInfo = new UserInfoDto(userRepository.findByUserNo(userId));
            if (userInfo.getUserPriority() == null) return userPriority;
            value = userInfo.getUserPriority();
            saveUserPriorityRedis(userId, value);
        }
        if (userPriorityRedis.getValue() != null) {
            value = userPriorityRedis.getValue();
            userPriority = Arrays.asList(value.split(":"));
        }
        return userPriority;
    }

    /**
     * redis에 임시로저장된 데이터를 바탕으로 새로운 priority 가져온다
     *
     * @param userId
     */
    public void makeUserPriority(long userId) {
        //redis에 없으면 그냥 return
        AllReview allReview = allReviewRepository.findById(userId).orElse(null);
        if (allReview != null) {
            createUserPriority(userId, allReview.getValue());
        }

    }

    /**
     * 레디스 저장
     *
     * @author hyejinzz
     * @since 2021-06-20
     **/

    private void saveUserPriorityRedis(long userId, String value) {
        UserPriorityRedis up = UserPriorityRedis.builder().userNo(userId).value(value).build();
        userPriorityRedisRepository.save(up);
    }

    /**
     * 유저평점을 바탕으로 userpriority 만들기 (최신 5개만)
     *
     * @author hyejinzz
     * @since 2021-06-27
     **/

    private void createUserPriority(long userNo, List<ReviewInfo> value) {

        //4개보다 크면 최신 5개만 가져온다
        if (value.size() > SemoConstant.CHECK_USER_REVIEW_CNT) {
            int cutSize = SemoConstant.CHECK_USER_REVIEW_CNT + 1;
            value = value.subList(value.size() - cutSize, value.size());
        }
        Map<String, Integer> map = new HashMap<>();
        for (ReviewInfo reviewInfo : value) {
            String key = reviewInfo.getCategory();
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        List<String> sortKey = new ArrayList<>(map.keySet());
        Collections.sort(sortKey, (o1, o2) -> map.get(o2).compareTo(map.get(o1)));
        //redis 저장
        StringBuilder saveValue = new StringBuilder();
        for (String l : sortKey) {
            saveValue.append(l).append(SemoConstant.COLON);
        }
        saveValue.deleteCharAt(saveValue.lastIndexOf(SemoConstant.COLON));
        userPriorityRedisRepository.save(UserPriorityRedis.builder()
                .userNo(userNo)
                .value(saveValue.toString())
                .build());


    }



    public UserResponse mailSend(MailRequest mailRequest) {
        String hMessage = "";
        StatusEnum hCode = null;
        Object data = null;

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("hyunho058@naver.com");
            message.setFrom("lhyun058@gmail.com");
            message.setSubject(mailRequest.getTitle());
            message.setText(mailRequest.getMessage());

            mailSender.send(message);


            hCode = StatusEnum.hd1004;
            data = mailRequest;
            hMessage = "정보 조회 성공";
        }catch (Exception e){
            log.info(":: userInfo err :: error is {} ", e);
            hCode = StatusEnum.hd4444;
            hMessage = "정보 조회 실패";
        }


        return UserResponse.builder()
                .code(hCode)
                .message(hMessage)
                .data(data)
                .build();
    }


}
