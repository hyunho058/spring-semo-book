package com.semobook.test;

import com.semobook.book.dto.BookDto;
import com.semobook.book.repository.BookRepository;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.bookReview.dto.BookReviewRequest;
import com.semobook.bookReview.dto.BookReviewWithIsbnDto;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest //스프링이 시작될때야만 실행되는 조건
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
    @DisplayName("리뷰가 저장되야한다")
    void createReviewTest(){
        //give

        BookReviewRequest rq = BookReviewRequest.builder()
                .userNo(99999L)
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
        bookReviewService.createReview(rq);

        //then
        Optional<BookReview> first = bookReviewRepository.findAllByUserInfo(99999, PageRequest.of(0, 999)).stream().findFirst();

        //북리뷰 조회에 유저가 쓴 리뷰가 있는지 확인해야 한다

        System.out.println(first.get().getBook().getIsbn());
        assertTrue(first.get().getBook().getIsbn().equals("9788901214924"));
    }

    @Test
    @DisplayName("유저 리뷰리스트")
    void readMyReviewTest(){
        //give
        UserInfo userInfo = UserInfo.builder()
                .userNo(99999L)
                .userId("userA@semo.com")
                .userPw("semo1234")
                .userName("userA")
                .userGender("M")
                .userBirth("19920519")
                .build();


        BookReviewRequest rq1 = BookReviewRequest.builder()
                .userNo(99999L)
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



        BookReviewRequest rq2 = BookReviewRequest.builder()
                .userNo(99999L)
                .isbn("9788901214924")
                .rating(4)
                .reviewContents("재미")
                .book(BookDto.builder()
                        .isbn("9788901214925")
                        .bookName("한 권으로 읽는 조선왕조실록")
                        .author("박영규")
                        .publisher("웅진지식하우스")
                        .kdc("900")
                        .category("900")
                        .img("http://image.kyobobook.co.kr/images/book/large/924/l9788901214924.jpg")
                        .build())
                .build();
        //when
        userRepository.save(userInfo);
        bookReviewService.createReview(rq1);
        bookReviewService.createReview(rq2);


        //then
        Page<BookReview> page = bookReviewRepository.findAllByUserInfo_userNo(99999L, PageRequest.of(0, 5));
        List<BookReviewWithIsbnDto> allReview = page.getContent().stream()
                .map(bookReview -> new BookReviewWithIsbnDto(bookReview))
                .collect(Collectors.toList());

        List<String> resutChk = Arrays.asList(new String[]{"800", "300"});

        List<String> result = allReview.stream().map(a-> a.getBookDto().getIsbn()).collect(Collectors.toList());

        assertTrue(resutChk.containsAll(result));
        //사이즈 확인

        //데이터 값 확인

    }

    @Test
    @DisplayName("리뷰 작성")
    void existsReviewTest(){
        //give

        //when

        //then
    }





}
