package com.semobook.bookwant.service;

import com.semobook.book.domain.Book;
import com.semobook.book.dto.BookWithReviewDto;
import com.semobook.book.repository.BookRepository;
import com.semobook.bookReview.dto.BookReviewResponse;
import com.semobook.bookwant.domain.BookWant;
import com.semobook.bookwant.dto.BookWantCreateRequest;
import com.semobook.bookwant.dto.BookWantDto;
import com.semobook.bookwant.dto.BookWantResponse;
import com.semobook.bookwant.repository.BookWantRepository;
import com.semobook.common.StatusEnum;
import com.semobook.user.domain.UserInfo;
import com.semobook.user.domain.UserStatus;
import com.semobook.user.dto.UserInfoDto;
import com.semobook.user.repository.UserRepository;
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

    private final BookWantRepository bookWantRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookWantResponse createPreference(BookWantCreateRequest bookWantCreateRequest) {
        log.info(":: createPreference :: start ");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;
        try {

            String requestIsbn = bookWantCreateRequest.getIsbn();
            Long requestUserNo = bookWantCreateRequest.getUserNo();
//            BookWant findbookWant =
            BookWantDto findBookWant;
            try {
                findBookWant = new BookWantDto(bookWantRepository.findAllByUserInfo_UserIdAndBook_Isbn(requestUserNo, requestIsbn));
            }catch (Exception e){
                findBookWant = null;
            }

            // TODO: 2021-06-10 책이 없을때 체크
            //선호도 검색
            if (findBookWant != null) {
                log.info(":: createPreference err :: already exist : findBook want is {}", findBookWant);
                hCode = StatusEnum.hd4444;
                hMessage = "이미 존재하는 선호도";
                data = findBookWant;
            }

            Book book = bookRepository.findByIsbn(requestIsbn);
            BookWithReviewDto dto = new BookWithReviewDto(book);
            UserInfoDto userInfo = new UserInfoDto(userRepository.findByUserNo(requestUserNo));
            if (findBookWant == null) {

                BookWant bookWant = BookWant.builder()
                        .bookDto(dto)
                        .preference(bookWantCreateRequest.getPreference())
                        .userInfo(userInfo)
                        .build();
                bookWantRepository.save(bookWant);
                hCode = StatusEnum.hd1004;
                hMessage = "createPreference 생성 성공";
                data = bookWant;
            }

        } catch (Exception e) {
            log.error(":: createPreference err :: error msg : {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "createPreference 에러";
            data = null;

        }

        return BookWantResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }

    @Transactional
    public BookWantResponse deletePreference(long wantNo) {
        log.info(":: deletePreference :: start ");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;
        try {
            int num = bookWantRepository.deleteBookByWantNo(wantNo);
            hCode = StatusEnum.hd1004;
            hMessage = "deletePreference 성공";
            data = num;
        } catch (Exception e) {
            log.error(":: deletePreference err :: error msg : {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "deletePreference 에러";
            data = null;

        }

        return BookWantResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }


    public BookWantResponse getPreference(Long userNo) {
        log.info(":: getPreference :: start ");
        String hMessage = null;
        Object data = null;
        StatusEnum hCode = null;

        try {
            List<BookWant> bookWants = bookWantRepository.findAll(userNo);
            if (bookWants.size() == 0) {
                hCode = StatusEnum.hd4444;
                hMessage = "데이터가 없음";
            } else {
                data = bookWants;
                hCode = StatusEnum.hd1004;
                hMessage = "getPreference 성공";
            }
        } catch (Exception e) {
            log.error(":: getPreference err :: error msg : {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "getPreference 에러";
            data = null;
        }

        return BookWantResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }

}

