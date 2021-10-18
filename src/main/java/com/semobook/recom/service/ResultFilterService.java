package com.semobook.recom.service;

import com.semobook.book.domain.Book;
import com.semobook.book.domain.RecomBestSeller;
import com.semobook.book.dto.BookDto;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.bookReview.repository.BookReviewRepository;
import com.semobook.bookwant.domain.BookWant;
import com.semobook.bookwant.dto.Preference;
import com.semobook.bookwant.repository.BookWantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultFilterService {

    private final BookWantRepository bookWantRepository;
    private final BookReviewRepository bookReviewRepository;


    /**
     * 중복책 제거
     *
     * @author hyejinzz
     * @since 2021-07-18
     **/
    public List<BookDto> bookDtoListDistinct(List<BookDto> bookList) {
        String isbn = null;
        for (int i = 0; i < bookList.size(); i++) {
            isbn = bookList.get(i).getIsbn();
            if (isbn == null) continue;
            for (int j = 0; j < bookList.size(); j++) {
                if (i == j) break;
                if (isbn.equals(bookList.get(j).getIsbn())) {
                    bookList.get(j).setIsbn(null);
                    break;
                }
            }
        }

        return bookList.stream().filter(a -> a.getIsbn() != null).collect(Collectors.toList());
    }

    /**
     * 중복된 책 제거, 20권이 넘으면 삭제
     *
     * @param bookList
     * @return
     * @author hyejinzz
     * @since 2021-06-19
     */
    public List<BookDto> bookDtoListCutter(List<BookDto> bookList) {
        Collections.shuffle(bookList);
        if (bookList.size() > 20) {
            bookList = bookList.subList(0, 20);
        }
        return bookList;
    }


    /**
     * 책 filter (유저가 싫어요 한 책, 유저가 이미 쓴 책)
     *
     * @author hyunho
     * @since 2021/07/11
     **/
    public List<BookDto> bookListFilter(List<BookDto> recomBestSellersList, long userNo) {
        Page<BookWant> likeAllByUserInfo = bookWantRepository.findLikeAllByUserInfo(userNo, Preference.DISLIKE, PageRequest.of(0, 100));
        Page<BookReview> allByUserInfo_userNo = bookReviewRepository.findAllByUserInfo_userNo(userNo, PageRequest.of(0, 100));
        recomBestSellersList.stream().filter(a-> likeAllByUserInfo.stream().noneMatch(b-> a.getIsbn().equals(b.getBook().getIsbn()))).collect(Collectors.toList());
        recomBestSellersList.stream().filter(a-> allByUserInfo_userNo.stream().noneMatch(b-> a.getIsbn().equals(b.getBook().getIsbn()))).collect(Collectors.toList());

        return recomBestSellersList;
    }
}
