package com.semobook.recom.controller;


import com.semobook.recom.dto.RecomResponse;
import com.semobook.recom.service.UserRandomEvaluation;
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

    private final UserRandomEvaluation userRandomEvaluation;
    private final RecomService recomService2;

    //유저 책 추천 리스트 가져오기기
    @Operation(description = "유저 책 가져오기")
    @GetMapping(value = "/book-list")
    public ResponseEntity<RecomResponse> userRecom(@Parameter @RequestParam(name = "userNo") long userNo) {
        return ResponseEntity.ok(recomService2.recomByUser(userNo));
    }

    //유저 성향 가져오기
    @Operation(description = "유저 성향 가져오기")
    @GetMapping(value = "/priority")
    public ResponseEntity<RecomResponse> userPriority(@Parameter @RequestParam(name = "userNo") long userNo) {
        return ResponseEntity.ok(userRandomEvaluation.userRandomEvaluation(userNo));
    }


}
