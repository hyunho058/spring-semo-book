package com.semobook.test;

import com.semobook.book.domain.Book;
import com.semobook.book.dto.BookDto;
import com.semobook.book.repository.BookRepository;
import com.semobook.book.service.BestSellerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
public class BookTest {
    @Autowired
    BestSellerService bestSellerService;
    @Autowired
    BookRepository bookRepository;

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
}
