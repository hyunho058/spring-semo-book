package com.semobook.user.controller;

import com.semobook.user.dto.*;
import com.semobook.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "UserController")
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //모든 회원 조회
    @Operation(description = "모든 회원조회")
    @GetMapping(value = "/users{page}")
    public ResponseEntity<UserResponse> getUserAllCon(@Parameter @PathVariable int page) {
        return ResponseEntity.ok(userService.findAllUser(page));
    }


    //id로 회원조회
    @Operation(description = "회원조회")
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserResponse> getUserByUserIdCon(@Parameter @PathVariable String id) {
        return ResponseEntity.ok(userService.findByUserId(id));
    }

    //회원가입
    @Operation(description = "회원가입")
    @PostMapping("/user/new")
    public ResponseEntity<UserResponse> signUpCon(@Parameter @RequestBody UserSignUpRequest userSignUpRequest) {
        return ResponseEntity.ok(userService.signUp(userSignUpRequest));
    }

    //회원탈퇴
    @Operation(description = "회원탈퇴")
    @DeleteMapping("/user")
    public ResponseEntity<UserResponse> deleteUserCon(@Parameter @RequestBody UserDeleteRequest userDeleteRequest) {
        return ResponseEntity.ok(userService.deleteUser(userDeleteRequest));
    }

    //회원정보 수정
    @Operation(description = "회원정보수정")
    @PutMapping("/user")
    public ResponseEntity<UserResponse> updateUserCon(@Parameter @RequestBody UserChangeUserInfoRequest updateUser) {
        return ResponseEntity.ok(userService.updateUser(updateUser));
    }

    //로그인
    @Operation(description = "로그인")
    @PostMapping(value = "/signin")
    public ResponseEntity<UserResponse> signInCon(@Parameter @RequestBody UserSignInRequest userSignInRequest) {
        return ResponseEntity.ok(userService.signIn(userSignInRequest));
    }

    @Operation(description = "회원 정보")
    @GetMapping("/user/info/{userNo}")
    public ResponseEntity<UserResponse> userInfoWithReviewCountCon(@Parameter @PathVariable long userNo){
        return ResponseEntity.ok(userService.userInfoWithReviewCount(userNo));
    }

    @Operation(description = "send email")
    @PostMapping("/user/help/email")
    public ResponseEntity<UserResponse> sendEmailCon(@Parameter @RequestBody MailRequest mailRequest){
        return ResponseEntity.ok(userService.mailSend(mailRequest));
    }

    @Operation(description = "회원의 성향 가져오기")
    @GetMapping("/priority")
    public ResponseEntity<UserResponse> userProiroty(@Parameter @RequestParam(name = "userNo") long userNo){
        return ResponseEntity.ok(userService.getUserReviewInfo(userNo));
    }

    @Operation(description = "find pw")
    @GetMapping("/user/help/pwInquiry")
    public ResponseEntity<UserResponse> findIdCon(@Parameter @RequestParam(name = "userId") String userId){
        return ResponseEntity.ok(userService.findPw(userId));
    }

}