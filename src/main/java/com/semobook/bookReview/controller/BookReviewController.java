package com.semobook.bookReview.controller;

import com.semobook.bookReview.dto.*;
import com.semobook.bookReview.dto.request.MonthBookReviewRequest;
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
@RequestMapping("/api")
@RestController
public class BookReviewController {

    private final BookReviewService boardService;

    //create
    @PostMapping("/bookreview/new")
    public ResponseEntity<BookReviewResponse> createBookReviewCon(@Parameter @RequestBody BookReviewRequest boardRequest){
        return ResponseEntity.ok(boardService.createReview(boardRequest));
    }

    //update
    @PutMapping("/bookreview")
    public ResponseEntity<BookReviewResponse> updateBookReviewCon(@Parameter @RequestBody BookUpdateRequest request){
        return ResponseEntity.ok(boardService.updateReview(request));
    }

    //도서 별점만 주기(도서 선호도)
    @PutMapping("/bookreview/rating")
    public ResponseEntity<BookReviewResponse> readRatingBookReviewCon(@Parameter @RequestBody BookReviewRatingRequest request){
        return ResponseEntity.ok(boardService.bookReviewRating(request));
    }

    //리뷰 조회
    @GetMapping("/bookreview")
    public ResponseEntity<BookReviewResponse> allBookReviewCon(@Parameter @RequestParam(name = "userNo") long userNo, @RequestParam(name = "page") int pageNum){
        log.info(":: AllBookReview() :: userNo is {}", userNo);
        return ResponseEntity.ok(boardService.readMyReview(userNo, pageNum));
    }

    //리뷰 전체 조회(패이징 적용)
    @GetMapping("/bookreviews/{page}")
    public ResponseEntity<BookReviewResponse> readBookReviewCon(@Parameter @PathVariable int page){
        return ResponseEntity.ok(boardService.readReviewAll(page));
    }

    //리뷰 삭제
    @DeleteMapping("/bookreview")
    public ResponseEntity<BookReviewResponse> deleteBookReviewCon(@Parameter @RequestBody DeleteBookReviewRequest deleteBookReviewRequest){
        return ResponseEntity.ok(boardService.deleteReview(deleteBookReviewRequest));
    }

    //월별 리뷰 조회
    @PostMapping("/bookreview/month")
    public ResponseEntity<BookReviewResponse> monthReviewCon(@Parameter @RequestBody MonthBookReviewRequest monthBookReviewRequest){
        return ResponseEntity.ok(boardService.monthReview(monthBookReviewRequest));
    }

    //유저별 도서 리뷰리스트
    @GetMapping("/bookreview/review-list")
    public ResponseEntity<BookReviewResponse> bookReviewListCon(@Parameter @RequestParam(name = "isbn") String isbn, @RequestParam(name = "page") int pageNum){
        return ResponseEntity.ok(boardService.bookReviewList(isbn, pageNum));
    }

}
