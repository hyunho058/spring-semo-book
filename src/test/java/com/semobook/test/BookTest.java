package com.semobook.test;

import com.semobook.book.domain.Book;
import com.semobook.book.dto.BookDto;
import com.semobook.book.dto.BookWithReviewDto;
import com.semobook.book.repository.BookRepository;
import com.semobook.book.service.BestSellerService;
import com.semobook.bookReview.dto.BookReviewRequest;
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

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@Transactional
public class BookTest {
    @Autowired
    BestSellerService bestSellerService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookReviewRepository bookReviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookReviewService bookReviewService;

    @Test
    @DisplayName("베스트셀러를_가져온다")
    void BEST_SELLSR_TEST() {
        List<BookDto> listA = bestSellerService.getBestSellerList("A", 10);
        listA.forEach(System.out::println);
        List<BookDto> list100 = bestSellerService.getBestSellerList("100", 10);
        list100.forEach(System.out::println);
        List<BookDto> list200 = bestSellerService.getBestSellerList("200", 10);
        list200.forEach(System.out::println);
    }

    @Test
    @DisplayName("스테디셀러를_가져온다")
    void STEADY_SELLER_TEST() throws Exception {
        List<BookDto> listA = bestSellerService.getSteadySellerList("A", 10);
        listA.forEach(System.out::println);
        List<BookDto> list100 = bestSellerService.getSteadySellerList("100", 10);
        list100.forEach(System.out::println);
        List<BookDto> list200 = bestSellerService.getSteadySellerList("200", 10);
        list200.forEach(System.out::println);
    }

    @Test
    @DisplayName("REDIS_DB_API 순으로 가져온다")
    void THREE_STEP_TEST() throws Exception {
        //given
        //when
        //then

    }

    @Test
    @DisplayName("도서_검색_ISBN")
    void 도서_검색_ISBN(){
        //give
        String isbn = "11111111";
        Book book = bookRepository.save(Book.builder()
                .isbn(isbn)
                .bookName("SEMO")
                .author("SEMO")
                .publisher("hDream")
                .kdc("800")
                .category("800")
                .keyword("800")
                .img("http://image.kyobobook.co.kr/images/book/large/924/l9788901214924.jpg")
                .build());
        //when
        bookRepository.save(book);
        BookDto bookDto = new BookDto(bookRepository.findByIsbn(isbn));

        //then
        assertThat(bookDto.getIsbn(), is(isbn));
    }

    @Test
    @DisplayName("도서_리뷰조회_ISBN")
    void 도서_리뷰조회_ISBN(){
        //give
        String isbn = "11111111";
        Book book = bookRepository.save(Book.builder()
                .isbn(isbn)
                .bookName("SEMO")
                .author("SEMO")
                .publisher("hDream")
                .kdc("800")
                .category("800")
                .keyword("800")
                .img("http://image.kyobobook.co.kr/images/book/large/924/l9788901214924.jpg")
                .build());

        UserInfo userInfo = UserInfo.builder()
                .userNo(99999L)
                .userId("userA@semo.com")
                .userPw("semo1234")
                .userName("userA")
                .userGender("M")
                .userBirth("19920519")
                .build();

        UserInfo userInfo1 = UserInfo.builder()
                .userNo(88888L)
                .userId("userB@semo.com")
                .userPw("semo1234")
                .userName("userB")
                .userGender("M")
                .userBirth("19920518")
                .build();

        BookReviewRequest rq1 = BookReviewRequest.builder()
                .userNo(99999L)
                .isbn(isbn)
                .rating(4)
                .reviewContents("재미")
                .book(new BookDto(book))
                .build();

        BookReviewRequest rq2 = BookReviewRequest.builder()
                .userNo(88888L)
                .isbn(isbn)
                .rating(3)
                .reviewContents("재미11")
                .book(new BookDto(book))
                .build();
        //when
        bookRepository.save(book);
        userRepository.save(userInfo);
        userRepository.save(userInfo1);
        bookReviewService.createReview(rq1);
        bookReviewService.createReview(rq2);

        BookWithReviewDto bookWithReviewDto = new BookWithReviewDto(bookRepository.findByIsbnWithReview(isbn));
        //then
        assertThat(bookWithReviewDto.getBookReviews().size(), is(2));
    }

    @Test
    @DisplayName("도서_리스트")
    void 도서_리스트(){
        //give
        int isbn = 11111111;
        for (int i = 0; i < 11; i++){
            Book book = bookRepository.save(Book.builder()
                    .isbn(String.valueOf(isbn++))
                    .bookName("SEMO"+i)
                    .author("SEMO")
                    .publisher("hDream")
                    .kdc("800")
                    .category("800")
                    .keyword("800")
                    .img("http://image.kyobobook.co.kr/images/book/large/924/l9788901214924.jpg")
                    .build());
            bookRepository.save(book);
        }
        //when
        int pageNum = 0;
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        Page<Book> page = bookRepository.findAll(pageRequest);
        //then

        assertThat(page.getTotalElements(), is(11L));
        assertThat(page.getTotalPages(), is(3));
        assertThat(page.isFirst(), is(true));




    }
}
