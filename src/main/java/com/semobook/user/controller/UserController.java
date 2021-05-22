package com.semobook.user.controller;

import com.semobook.user.dto.UserRequest;
import com.semobook.user.dto.UserResponse;
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


    private final
    UserService userService;


    @GetMapping(value = "/")
    public String userrootpage() {
        return "user root page";
    }

    //for Test search All Members
    @Operation(description = "회원조회")
    @GetMapping(value = "/all")
    public ResponseEntity<UserResponse> getUserAll() {
        return ResponseEntity.ok(userService.findAllUser());
    }


    //회원조회
    @Operation(description = "회원조회")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> getUser(@Parameter @PathVariable String id) {
        return ResponseEntity.ok(userService.findByUserId(id));
    }

    //로그인
    @Operation(description = "로그인")
    @PostMapping(value = "/signin")
    public ResponseEntity<UserResponse> signIn(@Parameter @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.signIn(userRequest));
    }

    //    //회원가입
    @Operation(description = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Parameter @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.signUp(userRequest));
    }

    //jiratest
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
