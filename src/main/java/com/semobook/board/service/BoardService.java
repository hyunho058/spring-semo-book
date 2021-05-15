package com.semobook.board.service;

import com.semobook.board.domain.Board;
import com.semobook.board.dto.BoardRequest;
import com.semobook.board.dto.BoardResponse;
import com.semobook.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardService {

    @Autowired
    private final BoardRepository boardRepository;


    //글 등록
    public BoardResponse addBoard(BoardRequest boardRequest) {
        try {

            //글 등록을 하면 바로 redis에 관련 책 저장
            boardRepository.save(Board.builder().build());

            //평점  3점 이상이면 recom으로 추천 업뎃치기

        }catch (Exception e){
            log.error("addBoard err :: error msg : {}",e);

        }
        finally {
            return BoardResponse.builder().build();
        }
    }
    //글 삭제
    //평가 등록



    private void recom(BoardRequest boardRequest){
        //1. db에서 관련도서 항목꺼내기
        //2. null 이면 카테고리 꺼내기
        //   2-1 카테고리가 일치하고
        //        평균 평점이 높은 순, 최신순으로 50개 가져옴
        //  이용자가 보기싫다고 한 데이터 삭제작업
        //  20개 이상이면 추천 업데이트
    }
}
