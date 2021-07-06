package com.semobook.bookReview.repository;

import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semobook.book.domain.Book;
import com.semobook.book.domain.QBook;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.bookReview.domain.QBookReview;
import com.semobook.user.domain.QUserInfo;
import jdk.internal.net.http.common.Log;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;

import static com.semobook.book.domain.QBook.*;
import static com.semobook.bookReview.domain.QBookReview.bookReview;
import static com.semobook.user.domain.QUserInfo.*;

@Slf4j
public class BookReviewRepositoryImpl implements BookReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BookReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * 리뷰 작성 여부 확인(한 책당 하나의 리뷰만 쓸수 있다.)
     *
     * @author hyunho
     * @since 2021/07/03
    **/
    @Override
    public boolean exists(long userNo, String isbn) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(bookReview)
                .where(bookReview.userInfo.userNo.eq(userNo).and(book.isbn.eq(isbn)))
                .fetchFirst();
        return fetchOne != null;
    }

    /**
     * PK(reviewNo) 를 잉용해 존재 여부 확인
     *
     * @author hyunho
     * @since 2021/07/03
    **/
    @Override
    public boolean existsByReviewNo(long reviewNo) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(bookReview)
                .where(bookReview.reviewNo.eq(reviewNo))
                .fetchFirst();
        return fetchOne != null;
    }


    /**
     * 유저 월별 리뷰 조회
     *
     * @author hyunho
     * @since 2021/07/03
    **/
//    https://www.inflearn.com/questions/193289
    @Override
    public List<BookReview> findByBookBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("startDate is = {}",startDate);
        List<BookReview> resultList = queryFactory
                .selectFrom(bookReview)
                .join(bookReview.userInfo, userInfo).fetchJoin()    //fetchJoin
                .join(bookReview.book, book).fetchJoin()
                .where(bookReview.createDate.between(startDate, endDate).and(bookReview.reviewContents .isNotNull()))
                .fetch();
        return resultList;
    }
}
