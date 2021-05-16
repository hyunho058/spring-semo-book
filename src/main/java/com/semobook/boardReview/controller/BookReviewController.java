package com.semobook.boardReview.controller;

import com.semobook.boardReview.dto.BookReviewRequest;
import com.semobook.boardReview.dto.BookReviewResponse;
import com.semobook.boardReview.service.BookReviewService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/create")
    public ResponseEntity<BookReviewResponse> createBookReview(@Parameter @RequestBody BookReviewRequest boardRequest){
        return ResponseEntity.ok(boardService.addBoard(boardRequest));
    }

    @GetMapping("/update")
    public ResponseEntity<BookReviewResponse> updateBookReview(@Parameter @RequestBody BookReviewRequest boardRequest){
        return ResponseEntity.ok(boardService.addBoard(boardRequest));
    }

    //
    @GetMapping("/read")
    public ResponseEntity<BookReviewResponse> readBookReview(@Parameter @RequestBody BookReviewRequest boardRequest){
        return ResponseEntity.ok(boardService.addBoard(boardRequest));
    }

    @GetMapping("/delete")
    public ResponseEntity<BookReviewResponse> deleteBookReview(@Parameter @RequestBody BookReviewRequest boardRequest){
        return ResponseEntity.ok(boardService.addBoard(boardRequest));
    }


    //read paging

    //update

    //delete
}
