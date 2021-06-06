package com.semobook.recom.controller;


import com.semobook.recom.dto.RecomResponse;
import com.semobook.recom.service.RecomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/recom")
@Tag(name = "추천 Controller" )
public class RecomController {

    private final RecomService recomService;


//    //관리자 추천
//    @Operation(description = "관리자 기반의 추천")
//    @PostMapping("/admin")
//    public String adminBasedRecom(){
//        return "";
//    }
//
//    //유저가 읽은 책 기반 추천
    @Operation(description = "유저가 읽은 책 기반의 추천")
    @GetMapping("/readbook")
    public ResponseEntity<RecomResponse> userReview(long userId){
        return ResponseEntity.ok(recomService.getUserReviewRecom(userId));
    }



//
//    //유저 정보 기반 추천
//    @Operation(description = "유저 정보(성별,나이) 기반의 추천")
//    @PostMapping("/userinfo")
//    public String userInfoBasedRecom(){
//        return "";
//    }
//
//
    //유저가 보고싶다고 찜해놓은 책들
    @Operation(description = "유저가 보고싶다고 찜한")
    @PostMapping("/userinfo")
    public String userWantBookRecom(){
        return "";
    }
//
//
//    //베스트 셀러
//    @Operation(description = "베스트 셀러 추천")
//    @PostMapping("/bestseller")
//    public String bestsellerRecom(){
//        return "";
//    }
//
//    @Operation(description = "베스트 셀러 추천")
//    @PostMapping("/bestseller")
//    public String bestsellerRecom(){
//        recomService.test();
//        return "haha";
//    }



}
