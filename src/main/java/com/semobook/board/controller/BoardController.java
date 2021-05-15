package com.semobook.board.controller;

import com.semobook.board.domain.Board;
import com.semobook.board.dto.BoardRequest;
import com.semobook.board.dto.BoardResponse;
import com.semobook.board.service.BoardService;
import com.semobook.book.dto.BookResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 리뷰 crud 컨트롤러
 *
 * @author jhj
 * @since 20210513
 *
 */

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Board Controller")
@RestController
public class BoardController {

    private final BoardService boardService;

    //create
    @GetMapping("/")
    public ResponseEntity<BoardResponse> writeBoard(@Parameter @RequestBody BoardRequest boardRequest){
        return ResponseEntity.ok(boardService.addBoard(boardRequest));
    }

    //read One

    //read paging

    //update

    //delete
}
