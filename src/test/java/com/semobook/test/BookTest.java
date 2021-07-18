package com.semobook.test;

import com.semobook.book.dto.BookDto;
import com.semobook.book.service.BestSellerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookTest {
    @Autowired
    BestSellerService bestSellerService;

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
}
