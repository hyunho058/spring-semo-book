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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //모든 회원 조회
    @Operation(description = "모든 회원조회")
    @GetMapping(value = "/all{page}")
    public ResponseEntity<UserResponse> getUserAll(@Parameter @PathVariable int page) {
        return ResponseEntity.ok(userService.findAllUser(page));
    }


    //id로 회원조회
    @Operation(description = "회원조회")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> getUserByUserId(@Parameter @PathVariable String id) {
        return ResponseEntity.ok(userService.findByUserId(id));
    }

    //로그인
    @Operation(description = "로그인")
    @PostMapping(value = "/signin")
    public ResponseEntity<UserResponse> signInCon(@Parameter @RequestBody UserSignInRequest userSignInRequest) {
        return ResponseEntity.ok(userService.signIn(userSignInRequest));
    }

    //회원가입
    @Operation(description = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUpCon(@Parameter @RequestBody UserSignUpRequest userSignUpRequest) {
        return ResponseEntity.ok(userService.signUp(userSignUpRequest));
    }

    //회원탈퇴
    @Operation(description = "회원탈퇴")
    @PostMapping("/delete")
    public ResponseEntity<UserResponse> deleteUserCon(@Parameter @RequestBody UserDeleteRequest userDeleteRequest) {
        return ResponseEntity.ok(userService.deleteUser(userDeleteRequest));
    }

    //회원정보 수정
    @Operation(description = "회원정보수정")
    @PostMapping("/update")
    public ResponseEntity<UserResponse> updateUserCon(@Parameter @RequestBody UserChangeUserInfoRequest updateUser) {
        return ResponseEntity.ok(userService.updateUser(updateUser));
    }

    @Operation(description = "회원 정보")
    @PostMapping("/userInfo")
    public ResponseEntity<UserResponse> userInfoWithReviewCountCon(@Parameter @RequestBody UserInfoRequest userInfoRequest){
        return ResponseEntity.ok(userService.userInfoWithReviewCount(userInfoRequest));
    }

    @Operation(description = "send email")
    @PostMapping("/send-email")
    public ResponseEntity<UserResponse> sendEmailCon(@Parameter @RequestBody MailRequest mailRequest){
        return ResponseEntity.ok(userService.mailSend(mailRequest));
    }

    @Operation(description = "회원의 성향 가져오기")
    @PostMapping("/priority")
    public ResponseEntity<UserResponse> userProiroty(@Parameter @RequestParam(name = "userNo") long userNo){
        return ResponseEntity.ok(userService.getUserReviewInfo(userNo));
    }

    @Operation(description = "find pw")
    @PostMapping("/find-pw")
    public ResponseEntity<UserResponse> findId(@Parameter @RequestBody FindPwRequest find){

        return ResponseEntity.ok(userService.findPw(find.getUserId()));
    }





}
