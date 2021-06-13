package com.semobook.bookReview.controller;

import com.semobook.bookReview.dto.*;
import com.semobook.bookReview.service.BookReviewService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 리뷰 crud 컨트롤러
 *
 * @author jhj
 * @since 20210513
 *
 */
//
@Slf4j
@RequiredArgsConstructor
@Tag(name = "BookReview Controller")
@RequestMapping("/bookreview")
@RestController
public class BookReviewController {

    private final BookReviewService boardService;

    //create
    @PostMapping("/create")
    public ResponseEntity<BookReviewResponse> createBookReview(@Parameter @RequestBody BookReviewRequest boardRequest){
        return ResponseEntity.ok(boardService.createReview(boardRequest));
    }

    //update
    @PostMapping("/update")
    public ResponseEntity<BookReviewResponse> updateBookReview(@Parameter @RequestBody BookUpdateRequest request){
        return ResponseEntity.ok(boardService.updateReview(request));
    }

    //도서 별점만 주기(도서 선소도)
    @PostMapping("/update/rating")
    public ResponseEntity<BookReviewResponse> readRatingBookReview(@Parameter @RequestBody BookReviewRatingRequest request){
        return ResponseEntity.ok(boardService.bookReviewRating(request));
    }

    //리뷰 조회
    @PostMapping("/read")
    public ResponseEntity<BookReviewResponse> AllBookReview(@Parameter @RequestBody BookSearchRequest boardRequest){
        log.info(":: AllBookReview() :: boardRequest is {}", boardRequest.getUserNo());
        return ResponseEntity.ok(boardService.readMyReview(boardRequest));
    }

    //리뷰 전체 조회(패이징 적용)
    @GetMapping("/all/{page}")
    public ResponseEntity<BookReviewResponse> readBookReview(@Parameter @PathVariable int page){
        return ResponseEntity.ok(boardService.readReviewAll(page));
    }

    //리뷰 삭제
    @PostMapping("/delete")
    public ResponseEntity<BookReviewResponse> deleteBookReview(@Parameter @RequestBody DeleteBookReviewRequest deleteBookReviewRequest){
        return ResponseEntity.ok(boardService.deleteReview(deleteBookReviewRequest));
    }

    // TODO: 2021-05-29 신고하기 기능 추가 필요

}
