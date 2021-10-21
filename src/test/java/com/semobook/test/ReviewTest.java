package com.semobook.test;

import com.semobook.book.dto.BookDto;
import com.semobook.book.repository.BookRepository;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.bookReview.dto.BookReviewDto;
import com.semobook.bookReview.dto.BookReviewRequest;
import com.semobook.bookReview.dto.BookReviewWithIsbnDto;
import com.semobook.bookReview.dto.request.MonthBookReviewRequest;
import com.semobook.bookReview.repository.AllReviewRepository;
import com.semobook.bookReview.repository.BookReviewRepository;
import com.semobook.bookReview.service.BookReviewService;
import com.semobook.user.domain.UserInfo;
import com.semobook.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest //스프링이 시작될때야만 실행되는 조건
@Transactional
public class ReviewTest {
    @Autowired
    BookReviewRepository bookReviewRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AllReviewRepository allReviewRepository;

    @Autowired
    BookReviewService bookReviewService;

    @Test
    @DisplayName("리뷰_저장")
    void 리뷰_저장(){
        //given
        String isbn = "9788901214924";
        String userId = "userA@semo.com";

        UserInfo userInfo = UserInfo.builder()
                .userId(userId)
                .userPw("semo1234")
                .userName("userA")
                .userGender("M")
                .userBirth("19920519")
                .build();
        userRepository.save(userInfo);
        UserInfo userData = userRepository.findByUserId(userId);

        BookReviewRequest rq = BookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .isbn(isbn)
                .rating(4)
                .reviewContents("재미")
                .book(BookDto.builder()
                        .isbn(isbn)
                        .bookName("한 권으로 읽는 조선왕조실록")
                        .author("박영규")
                        .publisher("웅진지식하우스")
                        .kdc("900")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/924/l9788901214924.jpg")
                        .build())
                .build();
        //when
        bookReviewService.createReview(rq);
        Optional<BookReview> first = bookReviewRepository.findAllByUserInfo_userNo(userData.getUserNo(), PageRequest.of(0, 100)).stream().findFirst();
        //then
        assertThat(first.get().getBook().getIsbn()).isEqualTo(isbn);
    }

    @Test
    @DisplayName("유저별_리뷰_리스트")
    void 유저별_리뷰_리스트(){
        //given
        String userId = "userA@semo.com";
        UserInfo userInfo = UserInfo.builder()
                .userId(userId)
                .userPw("semo1234")
                .userName("userA")
                .userGender("M")
                .userBirth("19920519")
                .build();
        userRepository.save(userInfo);
        UserInfo userData = userRepository.findByUserId("userA@semo.com");
        BookReviewRequest rq1 = BookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .isbn("9788901214924")
                .rating(4)
                .reviewContents("재미")
                .book(BookDto.builder()
                        .isbn("9788901214924")
                        .bookName("한 권으로 읽는 조선왕조실록")
                        .author("박영규")
                        .publisher("웅진지식하우스")
                        .kdc("900")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/924/l9788901214924.jpg")
                        .build())
                .build();

        BookReviewRequest rq2 = BookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .isbn("9788901219943")
                .rating(4)
                .reviewContents("재미")
                .book(BookDto.builder()
                        .isbn("9788901219943")
                        .bookName("신경 끄기의 기술")
                        .author("마크 맨슨")
                        .publisher("갤리온")
                        .kdc("500")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/943/l9788901219943.jpg")
                        .build())
                .build();
        //when
        bookReviewService.createReview(rq1);
        bookReviewService.createReview(rq2);


        //then
        Page<BookReview> page = bookReviewRepository.findAllByUserInfo_userNo(userData.getUserNo(), PageRequest.of(0, 5));
        List<BookReviewWithIsbnDto> reviews = page.getContent().stream()
                .map(bookReview -> new BookReviewWithIsbnDto(bookReview))
                .collect(Collectors.toList());
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(reviews.get(0).getBookDto().getIsbn()).isEqualTo("9788901214924");
        assertThat(reviews.get(1).getBookDto().getIsbn()).isEqualTo("9788901219943");

//        List<String> resultChk = Arrays.asList(new String[]{"800", "300"});
//
//        List<String> result = allReview.stream().map(a-> a.getBookDto().getIsbn()).collect(Collectors.toList());
//
//        assertTrue(resultChk.containsAll(result));

    }

    @Test
    @DisplayName("REVIEW_TOTAL_COUNT")
    void REVIEW_TOTAL_COUNT(){
        //given
        String userId = "userA@semo.com";
        UserInfo userInfo = UserInfo.builder()
                .userNo(99999L)
                .userId(userId)
                .userPw("semo1234")
                .userName("userA")
                .userGender("M")
                .userBirth("19920519")
                .build();
        userRepository.save(userInfo);
        UserInfo userData = userRepository.findByUserId(userId);
        BookReviewRequest rq1 = BookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .isbn("9788901214924")
                .rating(4)
                .reviewContents("재미")
                .book(BookDto.builder()
                        .isbn("9788901214924")
                        .bookName("한 권으로 읽는 조선왕조실록")
                        .author("박영규")
                        .publisher("웅진지식하우스")
                        .kdc("900")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/924/l9788901214924.jpg")
                        .build())
                .build();

        BookReviewRequest rq2 = BookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .isbn("9788901219943")
                .rating(4)
                .reviewContents("재미11111")
                .book(BookDto.builder()
                        .isbn("9788901219943")
                        .bookName("신경 끄기의 기술")
                        .author("마크 맨슨")
                        .publisher("갤리온")
                        .kdc("500")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/943/l9788901219943.jpg")
                        .build())
                .build();
        //when
        bookReviewService.createReview(rq1);
        bookReviewService.createReview(rq2);
        int bookTotal = bookReviewRepository.countReview(userData.getUserNo());
        //then
        assertThat(bookTotal).isEqualTo(2);
    }


    @Test
    @DisplayName("리뷰_작성_존재_확인")
    void 리뷰_작성_존재_확인(){
        //given
        String userId = "userA@semo.com";
        long userNo = 0;
        UserInfo userInfo = UserInfo.builder()
                .userNo(userNo)
                .userId(userId)
                .userPw("semo1234")
                .userName("userA")
                .userGender("M")
                .userBirth("19920519")
                .build();
        userRepository.save(userInfo);
        UserInfo userData = userRepository.findByUserId(userId);
        BookReviewRequest rq1 = BookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .isbn("9788901214924")
                .rating(4)
                .reviewContents("재미")
                .book(BookDto.builder()
                        .isbn("9788901214924")
                        .bookName("한 권으로 읽는 조선왕조실록")
                        .author("박영규")
                        .publisher("웅진지식하우스")
                        .kdc("900")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/924/l9788901214924.jpg")
                        .build())
                .build();
        //when
        bookReviewService.createReview(rq1);
        boolean exists = bookReviewRepository.exists(userData.getUserNo(), "9788901214924");
        //then
        assertThat(exists).isEqualTo(true);

    }

    @Test
    @DisplayName("유저_월별_리뷰")
    void 유저_월별_리뷰(){
        //given
        String userId = "userA@semo.com";
        UserInfo userInfo = UserInfo.builder()
                .userNo(99999L)
                .userId(userId)
                .userPw("semo1234")
                .userName("userA")
                .userGender("M")
                .userBirth("19920519")
                .build();
        userRepository.save(userInfo);
        UserInfo userData = userRepository.findByUserId(userId);
        BookReviewRequest rq1 = BookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .isbn("9788901214924")
                .rating(4)
                .reviewContents("재미")
                .book(BookDto.builder()
                        .isbn("9788901214924")
                        .bookName("한 권으로 읽는 조선왕조실록")
                        .author("박영규")
                        .publisher("웅진지식하우스")
                        .kdc("900")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/924/l9788901214924.jpg")
                        .build())
                .build();

        BookReviewRequest rq2 = BookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .isbn("9788901219943")
                .rating(4)
                .reviewContents("재미11111")
                .book(BookDto.builder()
                        .isbn("9788901219943")
                        .bookName("신경 끄기의 기술")
                        .author("마크 맨슨")
                        .publisher("갤리온")
                        .kdc("500")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/943/l9788901219943.jpg")
                        .build())
                .build();

        MonthBookReviewRequest monthBookReviewRequest = MonthBookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .startDate(LocalDateTime.of(2021,10,01,00,00))
                .endDate(LocalDateTime.of(2021,10,31,23,59))
                .build();


        //when

        bookReviewService.createReview(rq1);
        bookReviewService.createReview(rq2);

        List<BookReview> page = bookReviewRepository.findByBookBetweenDate(
                monthBookReviewRequest.getUserNo(),
                monthBookReviewRequest.getStartDate(),
                monthBookReviewRequest.getEndDate());

        //then
        assertThat(page.size()).isEqualTo(2);
//        assertThat(page.size(), is(2));

    }

    @Test
    @DisplayName("리뷰_내용이_작성된것만_조회")
    void 리뷰_내용이_작성된것만_조회(){
        //given
        String userId = "userA@semo.com";
        UserInfo userInfo = UserInfo.builder()
                .userNo(99999L)
                .userId(userId)
                .userPw("semo1234")
                .userName("userA")
                .userGender("M")
                .userBirth("19920519")
                .build();
        userRepository.save(userInfo);
        UserInfo userData = userRepository.findByUserId(userId);

        String reviewContents = "재미";
        BookReviewRequest rq1 = BookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .isbn("9788901214924")
                .rating(4)
                .reviewContents(reviewContents)
                .book(BookDto.builder()
                        .isbn("9788901214924")
                        .bookName("한 권으로 읽는 조선왕조실록")
                        .author("박영규")
                        .publisher("웅진지식하우스")
                        .kdc("900")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/924/l9788901214924.jpg")
                        .build())
                .build();
        BookReviewRequest rq2 = BookReviewRequest.builder()
                .userNo(userData.getUserNo())
                .isbn("9788901219943")
                .rating(4)
                .book(BookDto.builder()
                        .isbn("9788901219943")
                        .bookName("신경 끄기의 기술")
                        .author("마크 맨슨")
                        .publisher("갤리온")
                        .kdc("500")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/943/l9788901219943.jpg")
                        .build())
                .build();
        //when
        bookReviewService.createReview(rq1);
        bookReviewService.createReview(rq2);

        Page<BookReview> page = bookReviewRepository.findAllByUserInfoAndNotNullContents(
                userData.getUserNo(),
                PageRequest.of(0, 10)
        );

        List<BookReviewDto> results = page.getContent().stream()
                .map(bookReview -> new BookReviewDto(bookReview))
                .collect(Collectors.toList());
        //then
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(results.get(0).getReviewContents()).isEqualTo(reviewContents);
    }

}
