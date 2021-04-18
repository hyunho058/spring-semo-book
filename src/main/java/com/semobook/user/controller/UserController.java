package com.semobook.user.controller;

import com.semobook.user.dto.UserRequest;
import com.semobook.user.dto.UserResponse;
import com.semobook.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@Tag(name = "UserController")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final
    UserService userService;


    @GetMapping(value = "/")
    public String userrootpage() {
        return "user root page";
    }

    //회원조회
    @Operation(description = "회원조회")
    @PostMapping(value = "/getuser")
    public ResponseEntity<UserResponse> getUser(@RequestBody UserRequest userRequest) {
        log.info("==/getuser", userRequest.getUserId(), userRequest.getUserPassword());
        return ResponseEntity.ok(userService.findByUserId(userRequest.getUserId()));
    }

    //로그인
    @Operation(description = "로그인")
    @PostMapping(value = "/signin")
    public ResponseEntity<UserResponse> signIn(@RequestBody UserRequest userRequest) {
        log.info("==/signin : userRequest : id{} pw{}==", userRequest.getUserId(), userRequest.getUserPassword());
        return ResponseEntity.ok(userService.signIn(userRequest));
    }

    //    //회원가입
    @Operation(description = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody UserRequest userRequest) {
        log.info("/signup :: userId : {} :: userPw : {} :: userName : {} ===", userRequest.getUserId(), userRequest.getUserPassword(), userRequest.getUserName());
        return ResponseEntity.ok(userService.signUp(userRequest));
    }
//
//    //회원탈퇴
//    @ApiOperation(value = "회원상태변경")
//    @PostMapping("/withdraw")
//    public ResponseEntity<UserResponse> deleteUser(@RequestBody UserRequest userRequest) {
//        log.info("/signup :: userId : {} :: userPw : {} :: userName : {} ===", userRequest.getUserId(), userRequest.getUserPassword(), userRequest.getUserName());
//        return ResponseEntity.ok(userService.deleteUser(userRequest));
//    }
//
//
//    private void chkValidation(UserRequest request) {
//
//        if (request.getUserId() == null) {
//            /**
//             * TODO :
//             *  실패메시지 보내고 빈값 return하기
//             *  controller들 보내기 전에 chkValidation 하고 서비스 보내기
//             */
//            return;
//        }
//
//    }

}
