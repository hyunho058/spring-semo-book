package com.semobook.bookwant.service;

import com.semobook.bookReview.dto.BookReviewResponse;
import com.semobook.bookwant.domain.BookWant;
import com.semobook.bookwant.dto.BookWantCreateRequest;
import com.semobook.bookwant.dto.BookWantResponse;
import com.semobook.bookwant.repository.BookWantRepository;
import com.semobook.common.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookWantService {

    BookWantRepository bookWantRepository;

    @Transactional
    public BookWantResponse createPreference(BookWantCreateRequest bookWantCreateRequest) {
        log.info(":: createPreference :: start ");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;
        try {

            List<BookWant> bookWant = bookWantRepository.findAll(bookWantCreateRequest.getUserNo());
            //선호도 검색
            //이미 있는 선호도면 => 이미 있는 선호도
            //없으면 선호도 추가

        } catch (Exception e) {
            log.error(":: createReview err :: error msg : {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "createReview 에러";
            data = null;

        }

        return BookWantResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }
    @Transactional
    public BookWantResponse deletePreference(BookWantCreateRequest bookWantCreateRequest) {
        log.info(":: deletePreference :: start ");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;
        try {

        } catch (Exception e) {
            log.error(":: deletePreference err :: error msg : {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "createReview 에러";
            data = null;

        }

        return BookWantResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }


    public BookWantResponse getPreference(String id) {
        log.info(":: getPreference :: start ");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;
        try {

        } catch (Exception e) {
            log.error(":: getPreference err :: error msg : {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "createReview 에러";
            data = null;
        }

        return BookWantResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }

}

