package com.semobook.accessterms.controller;

import com.semobook.accessterms.dto.AccessTermsResponse;
import com.semobook.accessterms.service.AccessTermsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "AccessTerms Controller")
@RequestMapping("/accessterms")
@RequiredArgsConstructor
public class AccessTermsController {
    private final AccessTermsService accesstermsService;
    @Operation(description = "이용약관, 서비스 관리")
    @GetMapping("/findAllAccessTerms")
    public ResponseEntity<AccessTermsResponse> findAllAccessTerms(@Parameter @RequestParam(name = "accessNo") long accessNo){
        return ResponseEntity.ok(accesstermsService.findAll(accessNo));

    }
}
