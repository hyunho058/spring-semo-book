package com.semobook.boardReview.service;

import com.semobook.boardReview.domain.BookReview;
import com.semobook.boardReview.dto.BookReviewRequest;
import com.semobook.boardReview.dto.BookReviewResponse;
import com.semobook.boardReview.repository.BookReviewRepository;
import com.semobook.recom.service.RecomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
*
 * 리뷰 서비스
*
* @author hjjung
* @since 2021-05-16
**/
@RequiredArgsConstructor
@Slf4j
@Service
public class BookReviewService {

    private final BookReviewRepository boardRepository;
    private final RecomService recomService;


    //글 등록
    public BookReviewResponse addBoard(BookReviewRequest request) {
        String hCode;
        try {

            BookReview bookReview = request.getBookReview();
            //글 등록을 하면 바로 redis에 관련 책 저장
            boardRepository.save(BookReview.builder().build());
            //평점  3점 이상이면 recom으로 추천 업뎃치기
            if(bookReview.getRating()>=3){
                recomService.updateRecom(request);
            }

        }catch (Exception e){
            log.error("addBoard err :: error msg : {}",e);

        }
        finally {
            return BookReviewResponse.builder().build();
        }
    }

    //글 수정

    //글 삭제

    //평가 등록






        //1. db에서 관련도서 항목꺼내기
        //2. null 이면 카테고리 꺼내기
        //   2-1 카테고리가 일치하고
        //        평균 평점이 높은 순, 최신순으로 50개 가져옴
        //  이용자가 보기싫다고 한 데이터 삭제작업
        //  20개 이상이면 추천 업데이트



}
