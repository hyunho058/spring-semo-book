package com.semobook.bookReview.controller;

import com.semobook.bookReview.dto.BookReviewRequest;
import com.semobook.bookReview.dto.BookReviewResponse;
import com.semobook.bookReview.dto.BookSearchRequest;
import com.semobook.bookReview.dto.BookUpdateRequest;
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

    @PostMapping("/read/rating")
    public ResponseEntity<BookReviewResponse> readRatingBookReview(@Parameter @RequestBody BookSearchRequest request){
        return ResponseEntity.ok(boardService.readRatingReview(request));
    }

    @PostMapping("/read")
    public ResponseEntity<BookReviewResponse> AllBookReview(@Parameter @RequestBody BookReviewRequest boardRequest){
        return ResponseEntity.ok(boardService.readReview(boardRequest));
    }


    @GetMapping("/all")
    public ResponseEntity<BookReviewResponse> readBookReview(){
        return ResponseEntity.ok(boardService.readReviewAll());
    }

    @PostMapping("/delete")
    public ResponseEntity<BookReviewResponse> deleteBookReview(@Parameter @RequestBody long reviewNo){
        return ResponseEntity.ok(boardService.deleteReview(reviewNo));
    }

    // TODO: 2021-05-29 신고하기 기능 추가 필요

}
