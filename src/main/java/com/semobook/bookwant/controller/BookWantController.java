package com.semobook.bookwant.controller;

import com.semobook.bookwant.dto.BookWantCreateRequest;
import com.semobook.bookwant.dto.BookWantReadRequest;
import com.semobook.bookwant.dto.BookWantResponse;
import com.semobook.bookwant.service.BookWantService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 읽고싶은 책, 책 더이상 보이지 않기
 *
 * @author hyejinzz
 * @since 2021-06-06
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bookwant")
public class BookWantController {

    private final BookWantService bookWantService;

    //성향 추가
    @PostMapping("/create")
    public ResponseEntity<BookWantResponse> createBookWant(@Parameter @RequestBody BookWantCreateRequest bookWantCreateRequest) {
        return ResponseEntity.ok(bookWantService.createPreference(bookWantCreateRequest));
    }

    //성향 삭제
    @DeleteMapping("/{wantNo}")
    public ResponseEntity<BookWantResponse> deleteBookWant(@Parameter @PathVariable Long wantNo) {
        return ResponseEntity.ok(bookWantService.deletePreference(wantNo));
    }

    //id별 성향 가져오기
    @PostMapping("/{userId}")
    public ResponseEntity<BookWantResponse> getBookWant(@Parameter @RequestBody BookWantReadRequest request){
        return ResponseEntity.ok(bookWantService.getPreference(request));
    }

}
