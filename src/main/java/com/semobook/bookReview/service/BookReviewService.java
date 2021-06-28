package com.semobook.bookReview.service;

import com.semobook.book.domain.Book;
import com.semobook.book.repository.BookRepository;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.bookReview.dto.*;
import com.semobook.bookReview.dto.request.MonthBookReviewRequest;
import com.semobook.bookReview.repository.BookReviewRepository;
import com.semobook.common.StatusEnum;
import com.semobook.recom.service.RecomService;
import com.semobook.user.domain.UserInfo;
import com.semobook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 리뷰 서비스
 *
 * @author hjjung
 * @since 2021-05-16
 **/
@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final RecomService recomService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    /**
     * 글 등록
     *
     * @author hyejinzz
     * @since 2021/05/29
     **/
    @Transactional
    public BookReviewResponse createReview(BookReviewRequest request) {
        log.info("createReview ::");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        try {
            if (bookReviewRepository.exists(request.getUserNo(), request.getBook().getIsbn())){
                log.info("createReview:: review is existence");
                hCode = StatusEnum.hd4444;
                hMessage = "이미 리뷰를 등록하였습니다.";
                data = null;
            }else {
                log.info("createReview:: review is not existence");
                Book book;
                if (bookRepository.existsByIsbn(request.getBook().getIsbn())){
                    log.info("createReview:: book is existence");
                    book = bookRepository.findByIsbn(request.getBook().getIsbn());
                }else {
                    log.info("createReview:: book is not existence");
                    book = bookRepository.save(Book.builder()
                            .isbn(request.getBook().getIsbn())
                            .bookName(request.getBook().getBookName())
                            .author(request.getBook().getAuthor())
                            .publisher(request.getBook().getPublisher())
                            .kdc(request.getBook().getKdc())
                            .category(request.getBook().getCategory())
                            .keyword(request.getBook().getKeyword())
                            .img(request.getBook().getImg())
                            .build());
                }
                UserInfo resultUserInfo = userRepository.findByUserNo(request.getUserNo());
                log.info("createReview :: resultUserInfo is {}", resultUserInfo.getUserName());
                if (book != null && resultUserInfo != null) {
                    bookReviewRepository.save(BookReview.builder()
                            .rating(request.getRating())
                            .reviewContents(request.getReviewContents())
                            .createDate(LocalDateTime.now())
                            .declaration(0)
                            .book(book)
                            .userInfo(resultUserInfo)
                            .build());
                    //평점  3점 이상이면 recom으로 추천 업뎃치기
                    if (request.getRating() >= 3) {
//                    recomService.updateUserReviewRecom(request.getIsbn(),request.getUserNo());
                    }
                    hCode = StatusEnum.hd1004;
                    hMessage = "저장완료";
                    data = request;
                } else {
                    hCode = StatusEnum.hd4444;
                    hMessage = "저장실패";
                    data = null;
                }

            }

        } catch (Exception e) {
            log.error("createReview err :: error msg : {}", e);
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
     * 도서 별점 주기
     *
     * @author hyunho
     * @since 2021/06/13
    **/
    @Transactional
    public BookReviewResponse bookReviewRating(BookReviewRatingRequest request){
        log.info("bookReviewRating ::");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;
        try {
            if (bookReviewRepository.exists(request.getUserNo(), request.getBook().getIsbn())){
                log.info("bookReviewRating:: review is existence");
            }else {
                log.info("bookReviewRating:: review is not existence");
                Book book;
                if (bookRepository.existsByIsbn(request.getBook().getIsbn())){
                    log.info("bookReviewRating:: book is existence");
                    book = bookRepository.findByIsbn(request.getBook().getIsbn());
                }else {
                    log.info("bookReviewRating:: book is not existence");
                    book = bookRepository.save(Book.builder()
                            .isbn(request.getBook().getIsbn())
                            .bookName(request.getBook().getBookName())
                            .author(request.getBook().getAuthor())
                            .publisher(request.getBook().getPublisher())
                            .kdc(request.getBook().getKdc())
                            .category(request.getBook().getCategory())
                            .keyword(request.getBook().getKeyword())
                            .img(request.getBook().getImg())
                            .build());
                }
                UserInfo resultUserInfo = userRepository.findByUserNo(request.getUserNo());
                log.info("createReview :: resultUserInfo is {}", resultUserInfo.getUserName());
                if (book != null && resultUserInfo != null) {
                    bookReviewRepository.save(BookReview.builder()
                            .rating(request.getRating())
                            .createDate(LocalDateTime.now())
                            .declaration(0)
                            .book(book)
                            .userInfo(resultUserInfo)
                            .build());
                    //평점  3점 이상이면 recom으로 추천 업뎃치기
                    if (request.getRating() >= 3) {
//                    recomService.updateUserReviewRecom(request.getIsbn(),request.getUserNo());
                    }
                    hCode = StatusEnum.hd1004;
                    hMessage = "저장완료";
                    data = request;
                } else {
                    hCode = StatusEnum.hd4444;
                    hMessage = "저장실패";
                    data = null;
                }
            }


        } catch (Exception e) {
            log.error("createReview err :: error msg : {}", e);
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
     *
     * @author hyejinzz
     * @since 2021-05-29
     **/
    public BookReviewResponse readMyReview(BookSearchRequest request) {
        log.info(":: readMyReview() :: request is {}", request.getUserNo());
        String hMessage = "";
        Object data = null;
        StatusEnum hCode = null;

        try {
            int start = request.getStartPage();
            long userNo = request.getUserNo();

            Page<BookReview> page = bookReviewRepository.findAllByUserInfo_userNo(userNo, PageRequest.of(start, 10));
            List<BookReviewWithIsbnDto> allReview = page.getContent().stream()
                    .map(bookReview -> new BookReviewWithIsbnDto(bookReview))
                    .collect(Collectors.toList());

            hCode = StatusEnum.hd1004;
            hMessage = "가져오기";
            data = allReview;

        } catch (Exception e) {
            log.error("createReview err :: error msg : {}", e);
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


    /**
     * 모든 글
     * reference - https://www.inflearn.com/questions/14559
     *
     * @author hyejinzz, hyunho
     * @since 2021/05/30
     **/
    public BookReviewResponse readReviewAll(int pageNum) {
        log.info("showReview");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        try {
            Page<BookReview> page = bookReviewRepository.findAll(PageRequest.of(pageNum, 5));
            List<BookReviewWithBookDto> result = page.getContent().stream()
                    .map(bookReview -> new BookReviewWithBookDto(bookReview))
                    .collect(Collectors.toList());
            log.info("bookReviewList : {}", result.toString());

            hCode = StatusEnum.hd1004;
            hMessage = "가져오기";
            data = result;

        } catch (Exception e) {
            log.error("createReview err :: error msg : {}", e);
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
     * 모든 사람 글 보여주기
     *
     * @author hyejinzz, hyunho
     * @since 2021/05/19
     **/
    public BookReviewResponse readRatingReview(BookSearchRequest request) {
        log.info("readRatingReview");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        try {
            LocalDateTime today = LocalDateTime.now();
            List<BookReview> bookReviewList = bookReviewRepository.findAllByCreateDateBefore(today, PageRequest.of(request.getStartPage(), 5));
            log.info("bookReviewList : {}", bookReviewList.toString());

            hCode = StatusEnum.hd1004;
            hMessage = "모든 사람 글 가져오기";
            data = bookReviewList;

        } catch (Exception e) {
            log.error("readRatingReview err :: error msg : {}", e);
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

    /**
     * 글 수정
     *
     * @author hyejinzz
     * @since 2021/05/19
     **/
    @Transactional
    public BookReviewResponse updateReview(BookUpdateRequest request) {
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        log.info("updateReview");

        try {
            BookReview bookReview = bookReviewRepository.findByReviewNo(request.getReviewNo());
            bookReview.changeBookReview(request.getRating(), request.getReviewContents());

            //평점  3점 이상이면 recom으로 추천 업뎃치기
            if (bookReview.getRating() >= 3) {
//                recomService.updateUserReviewRecom(bookReview.getBook().getIsbn(),bookReview.getUserInfo().getUserNo());
            }

            hCode = StatusEnum.hd1004;
            hMessage = "글 수정완료";
            data = request;

        } catch (Exception e) {
            log.error("updateReview err :: error msg : {}", e);
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

    /**
     * 글 삭제
     *
     * @author hyejinzz, hyunho
     * @since 2021/05/30
     **/
    @Transactional
    public BookReviewResponse deleteReview(DeleteBookReviewRequest deleteBookReviewRequest) {
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;
        try {
            bookReviewRepository.deleteBookReviewByReviewNo(deleteBookReviewRequest.getReviewNo());
//            data = resultCode;
            hCode = StatusEnum.hd1004;
            hMessage = "삭제 완료";
        } catch (Exception e) {
            log.error("addBoard err :: error msg : {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "삭제 중 오류";
        }
        return BookReviewResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }

    public BookReviewResponse monthReview(MonthBookReviewRequest monthBookReviewRequest){
        log.info("monthReview()");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        try {
            Page<BookReview> page = bookReviewRepository.findAllByUserInfo(monthBookReviewRequest.getUserNo(), PageRequest.of(0, 100));
            List<BookReviewWithBookDto> result = page.getContent().stream()
                    .map(bookReview -> new BookReviewWithBookDto(bookReview))
                    .collect(Collectors.toList());
            log.info("BookReview list : {}", page.getTotalPages());

            hCode = StatusEnum.hd1004;
            hMessage = "가져오기";
            data = result;

        } catch (Exception e) {
            log.error("monthReview err :: error msg : {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "monthReview 에러";
            data = null;

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
