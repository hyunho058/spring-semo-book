package com.semobook.bookwant.controller;

import com.semobook.bookwant.dto.BookWantCreateRequest;
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
    @DeleteMapping("/delete")
    public ResponseEntity<BookWantResponse> deleteBookWant(@Parameter @RequestBody BookWantCreateRequest bookWantCreateRequest) {
        return ResponseEntity.ok(bookWantService.deletePreference(bookWantCreateRequest));
    }

    //id별 성향 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<BookWantResponse> getBookWant(@Parameter @PathVariable String id){
        return ResponseEntity.ok(bookWantService.getPreference(id));
    }

}
