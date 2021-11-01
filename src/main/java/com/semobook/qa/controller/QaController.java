package com.semobook.qa.controller;

import com.semobook.qa.dto.QaRequest;
import com.semobook.qa.dto.QaResponse;
import com.semobook.qa.service.QaService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Qa Controller")
@RequestMapping("/api")
@RequiredArgsConstructor
public class QaController {

    private final QaService qaService;

    @PostMapping("/qa/new")
    public ResponseEntity<QaResponse> createQaCon(@Parameter @RequestBody QaRequest request){
        return ResponseEntity.ok(qaService.createQa(request));
    }

    @GetMapping("/qs/list")
    public ResponseEntity<QaResponse> findAllQaCon(@Parameter @RequestParam(name = "userNo") long userNo, @RequestParam("page") int pageNum){
        return ResponseEntity.ok(qaService.findAllQa(userNo, pageNum));
    }

}
