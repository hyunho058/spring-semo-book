package com.semobook.recom.service;

import com.semobook.bookReview.dto.BookReviewRequest;
import com.semobook.book.domain.Book;
import com.semobook.book.repository.BookRepository;
import com.semobook.common.StatusEnum;
import com.semobook.recom.domain.RecomUserReview;
import com.semobook.recom.dto.RecomResponse;
import com.semobook.recom.repository.RecomBestSellerRepository;
import com.semobook.recom.repository.RecomUserInfoRepository;
import com.semobook.recom.repository.RecomUserReviewRepository;
import com.semobook.recom.repository.RecomUserTotalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecomService {

    //1순위 : 유저가 리뷰를 쓰면 -> 리뷰책과 관련된 장르의 책 추천 REDIS KEY : USER_REVIEW_RECOM
    //1. 이책을 본 사람들의 다른 책들 추천
    //2. 이책과 비슷한 장르 추천
    //2순위 : 유저가 종합적으로 준 평점 기반 추천 : USER_TOTAL_RECOM
    //3순위 : 유저 정보 (나이, 성별) 기반 추천 : USER_INFO_RECOM
    //4순위 : 베스트 셀러 추천 : BSETSELLER_RECOM

    private final BookRepository bookRepository;
    private final RecomBestSellerRepository bestSellerRepository;
    private final RecomUserInfoRepository userInfoRepository;
    private final RecomUserReviewRepository userReviewRepository;
    private final RecomUserTotalRepository recomUserTotalRepository;


    /**
     * 유저가 읽은 책 기반 추천
     *
     * @author hyejinzz
     * @since 2021-06-01
     **/

    public RecomResponse getUserReviewRecom(long userid) {
        Object data = null;
        StatusEnum hCode = StatusEnum.hd4444;
        String hMessage = "test";
        try {
            userid = 976;
            Optional<RecomUserReview> f = userReviewRepository.findById(userid);
            log.info(f.toString());
            data = f;
        } catch (Exception e) {

        }
        return RecomResponse.builder()
                .data(data)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }



    /**
     * 유저가 리뷰를 등록하면 관련도서 추천을 함
     * 1. 책에 keyword 있으면 keyword 같은 키워드 탐색
     * 2. 값이 부족하면 category로 같은 도서 탐색
     * 3. 값이 부족하면 kdc로 앞자리 같은 도서 탐색
     * 20개 채워지면 redis에 저장
     * 20개 채워지지 않으면 저장하지 않음
     *
     * @author hyejinzz
     * @since 2021-06-01
     **/
    // TODO: 2021-06-01 LIST<BOOK> 으로 만들어야한다
    public void updateUserReviewRecom(String isbn, long userNo) {
        Book book = bookRepository.findByIsbn(isbn);

        List<Book> bookList = new ArrayList<>();

        String bookName = book.getBookName();
        String author = book.getAuthor();
        String publisher = book.getPublisher();
        String kdc = book.getKdc();
        String category = book.getCategory();
        String keyword = book.getKeyword();
        String img = book.getImg();

//        //내가 읽은 책의 카테고리 가져온다.
        ;
        if (book.getKeyword() !=null){
            List<Book> recomBookList = bookRepository.findAllByKeyword(category);
            bookList.addAll(recomBookList);
        }
        if (category != null && bookList.size()<20) {
            //카테고리 같은 책들 가져오기
            List<Book> recomBookList = bookRepository.findAllByCategory(category);
            bookList.addAll(recomBookList);
        }

        try {
            userReviewRepository.save(RecomUserReview.builder()
                    .userNo(userNo)
                    .isbn(isbn)
                    .bookName(bookName)
                    .author(author)
                    .publisher(publisher)
                    .kdc(kdc)
                    .category(category)
                    .keyword(keyword)
                    .img(img)
                    .build());
        } catch (Exception e) {
            log.info(":: updateUserReviewRecom err :: error is {} ", e);
        }

    }


    /**
    * 이용자가 추천받고 싶지 않은 책들을 필터함
    * @author hyejinzz
    * @since 2021-06-03
    **/

   private List<Book> recomfilter(List<Book> recomList) {

//       recomList.stream().filter(i->)


        return null;
    }


}
