package com.semobook.bookReview.service;

import com.semobook.book.domain.Book;
import com.semobook.book.repository.BookRepository;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.bookReview.dto.BookReviewRequest;
import com.semobook.bookReview.dto.BookReviewResponse;
import com.semobook.bookReview.dto.BookSearchRequest;
import com.semobook.bookReview.dto.BookUpdateRequest;
import com.semobook.bookReview.repository.BookReviewRepository;
import com.semobook.common.StatusEnum;
import com.semobook.recom.service.RecomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    private final BookReviewRepository bookReviewRepository;
    private final RecomService recomService;
    private final BookRepository bookRepository;


    //글 등록
    public BookReviewResponse createReview(BookReviewRequest request) {
        log.info("createReview");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        try {
            Book reviewBook = bookRepository.findByIsbn(request.getIsbn());

            bookReviewRepository.save(BookReview.builder()
                    .book(reviewBook)
                    .rating(request.getRating())
                    .reviewContents(request.getReviewContents())
                    .createDate(LocalDateTime.now())
                    .declaration(0)
                    .build());
            //평점  3점 이상이면 recom으로 추천 업뎃치기
            if(request.getRating()>=3){
                recomService.updateRecom(request.getIsbn());
            }
            hCode = StatusEnum.hd1004;
            hMessage = "저장완료";
            data = request;

        }catch (Exception e){
            log.error("createReview err :: error msg : {}",e);
            hCode = StatusEnum.hd4444;
            hMessage = "createReview 에러";
            data = null;

        }

        return BookReviewResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }


  /**
  * 내 글 보여주기
  * @author hyejinzz
  * @since 2021-05-29
  **/

    //내 글 보여주기
    public BookReviewResponse readMyReview(BookSearchRequest request) {
        log.info("showReview");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        try {
            int start = request.getStartPage();
            long userNo = request.getUserNo();

            List<BookReview> allRevice = bookReviewRepository.findAllByUserInfo_userNo(userNo,PageRequest.of(start,5));

            hCode = StatusEnum.hd1004;
            hMessage = "가져오기";
            data = allRevice;

        }catch (Exception e){
            log.error("createReview err :: error msg : {}",e);
            hCode = StatusEnum.hd4444;
            hMessage = "readMyReview 에러";
            data = null;

        }

        return BookReviewResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
        }


        //모든 글
    public BookReviewResponse readReviewAll() {
        log.info("showReview");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        try {
            List<BookReview> bookReviewList = bookReviewRepository.findAll();
            log.info("bookReviewList : {}",bookReviewList.toString());

            hCode = StatusEnum.hd1004;
            hMessage = "가져오기";
            data = bookReviewList;

        }catch (Exception e){
            log.error("createReview err :: error msg : {}",e);
            hCode = StatusEnum.hd4444;
            hMessage = "readReview 에러";
            data = null;

        }

        return BookReviewResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
        }



    /**
     * TODO
     *  페이징처리가 필요합니다
     * @param request
     * @return
     */

    //모든 사람 글 보여주기
    public BookReviewResponse readRatingReview(BookSearchRequest request) {
        log.info("readRatingReview");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        try {
            LocalDateTime today = LocalDateTime.now();
            List<BookReview> bookReviewList = bookReviewRepository.findAllByCreateDateBefore(today,PageRequest.of(request.getStartPage(),5));
            log.info("bookReviewList : {}",bookReviewList.toString());

            hCode = StatusEnum.hd1004;
            hMessage = "모든 사람 글 가져오기";
            data = bookReviewList;

        }catch (Exception e){
            log.error("readRatingReview err :: error msg : {}",e);
            hCode = StatusEnum.hd4444;
            hMessage = "readRatingReview 에러";
            data = null;

        }

        return BookReviewResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }


    //글 수정
    public BookReviewResponse updateReview(BookUpdateRequest request) {
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        log.info("updateReview");

        try {
            BookReview bookReview = bookReviewRepository.findByReviewNo(request.getReviewNo());
            bookReview.changeBookReview(request.getRating(),request.getReviewContents());

            //평점  3점 이상이면 recom으로 추천 업뎃치기
            if(bookReview.getRating()>=3){
                recomService.updateRecom(bookReview.getBook().getIsbn());
            }

            hCode = StatusEnum.hd1004;
            hMessage = "글 수정완료";
            data = request;

        }catch (Exception e){
            log.error("updateReview err :: error msg : {}",e);
            hCode = StatusEnum.hd4444;
            hMessage = "updateReview 에러";
            data = null;

        }

        return BookReviewResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }

    //글 삭제
    public BookReviewResponse deleteReview(long reviewNo) {
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;
        try {
            bookReviewRepository.deleteBookReviewByReviewNo(reviewNo);
            hCode = StatusEnum.hd1004;
            hMessage = "삭제 완료";
        }catch (Exception e){
            log.error("addBoard err :: error msg : {}",e);
            hCode = StatusEnum.hd4444;
            hMessage = "삭제 중 오류";
        }
        return BookReviewResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }

    //평가 등록





        //1. db에서 관련도서 항목꺼내기
        //2. null 이면 카테고리 꺼내기
        //   2-1 카테고리가 일치하고
        //        평균 평점이 높은 순, 최신순으로 50개 가져옴
        //  이용자가 보기싫다고 한 데이터 삭제작업
        //  20개 이상이면 추천 업데이트



}
