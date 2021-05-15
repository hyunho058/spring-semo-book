package com.semobook.board.dto;

import com.semobook.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequest {
    Board board;
}
