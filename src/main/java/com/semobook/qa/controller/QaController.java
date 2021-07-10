package com.semobook.qa.controller;

import com.semobook.qa.dto.QaListRequest;
import com.semobook.qa.dto.QaRequest;
import com.semobook.qa.dto.QaResponse;
import com.semobook.qa.service.QaService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Qa Controller")
@RequestMapping("/qa")
@RequiredArgsConstructor
public class QaController {

    private final QaService qaService;

    @PostMapping("/create")
    public ResponseEntity<QaResponse> createQaCon(@Parameter @RequestBody QaRequest request){
        return ResponseEntity.ok(qaService.createQa(request));
    }

    @PostMapping("/findAllQa")
    public ResponseEntity<QaResponse> findAllQaCon(@Parameter @RequestBody QaListRequest request){
        return ResponseEntity.ok(qaService.findAllQa(request));
    }

}
