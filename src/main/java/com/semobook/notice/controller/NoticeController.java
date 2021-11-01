package com.semobook.notice.controller;


import com.semobook.notice.dto.NoticeResponse;
import com.semobook.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Notice Controller")
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeController {

    private  final NoticeService noticeService;

    @Operation(description = "공지사항 리스트 5개씩")
    @GetMapping("/notices")
    public ResponseEntity<NoticeResponse> findAllNotice(@Parameter @RequestParam(name = "pageNum") int pageNum){
        return ResponseEntity.ok(noticeService.findAll(pageNum));

    }

}
