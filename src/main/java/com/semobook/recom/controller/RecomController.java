package com.semobook.recom.controller;


import com.semobook.recom.dto.RecomResponse;
import com.semobook.recom.service.RecomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/recom")
@Tag(name = "추천 Controller")
public class RecomController {

    private final RecomService recomService;

    /**
     * 추천 요청하면 20개 * 5개 리스트 가져옴
     * 1순위 관리자 추천
     * 2순위 유저가 읽은 책 기반 추천 (유저가 리뷰쓴거) => 여러개
     * 3순위 유저가 보고싶다고 한 책
     * 5순위 유저 정보 (여성 , 나이) 추천
     * 6순위 유저 선호 카테고리별 추천
     * 7순위 종합 베스트 셀러
     *
     * @param userId
     * @return
     */


//
//    //유저가 읽은 책 기반 추천
//    @Operation(description = "유저가 읽은 책 기반의 추천")
//    @GetMapping("/readbook")
//    public ResponseEntity<RecomResponse> userReview(long userId) {
//        return ResponseEntity.ok(recomService.getUserReviewRecom(userId));
//    }
//
//    //유저가 보고싶다고 찜해놓은 책들
//    @Operation(description = "유저가 보고싶다고 찜한")
//    @PostMapping("/userinfo")
//    public String userWantBookRecom() {
//        return "";
//    }


    //유저 책 추천 리스트 가져오기기
    @Operation(description = "유저 책 가져오기")
    @GetMapping(value = "/book-list/{userNo}")
    public ResponseEntity<RecomResponse> userrecom(@Parameter @PathVariable long userNo) {
        return ResponseEntity.ok(recomService.recomByUser(userNo));
    }

    //유저 성향 가져오기
    @Operation(description = "유저 성향 가져오기")
    @GetMapping(value = "/priority")
    public ResponseEntity<RecomResponse> userPriority(@Parameter @RequestParam(name = "userNo") long userNo) {
        return ResponseEntity.ok(recomService.userRandomEvaluation(userNo));
    }


}
