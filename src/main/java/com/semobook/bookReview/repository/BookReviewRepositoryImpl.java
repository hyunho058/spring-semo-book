package com.semobook.bookReview.repository;

import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semobook.book.domain.Book;
import com.semobook.book.domain.QBook;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.bookReview.domain.QBookReview;
import jdk.internal.net.http.common.Log;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;

import static com.semobook.book.domain.QBook.*;
import static com.semobook.bookReview.domain.QBookReview.bookReview;

@Slf4j
public class BookReviewRepositoryImpl implements BookReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BookReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public boolean exists(long userNo, String isbn) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(bookReview)
                .where(bookReview.userInfo.userNo.eq(userNo).and(book.isbn.eq(isbn)))
                .fetchFirst();
        return fetchOne != null;
    }

    @Override
    public boolean existsByReviewNo(long reviewNo) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(bookReview)
                .where(bookReview.reviewNo.eq(reviewNo))
                .fetchFirst();
        return fetchOne != null;
    }


    //    2021-06-15 13:49:31.482941

//    select *
//    from book_review
//    where create_date
//    BETWEEN DATE_ADD(now(), INTERVAL -1 MONTH)
//    AND cast(now() as date)

//    select *
//    from book_review
//    where create_date
//    BETWEEN DATE_ADD(now(), INTERVAL -1 MONTH)
//    AND now();

    //java.time.LocalDate
//    LocalDate first = LocalDate.of(1990, 01, 01);
//    LocalDate last = LocalDate.of(1990, 02, 01);
//
//    return query.from(human)
//            .innerJoin(human.pet, pet)
//            .where(SQLExpressions.date(human.age).between(first, last))
//            .list(order);

//    https://www.inflearn.com/questions/193289
    @Override
    public List<BookReview> findByBookBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("startDate is = {}",startDate);
//        LocalDateTime startDate1 = LocalDateTime.of(2021, 06, 01, 00, 00, 00);
//        LocalDateTime endDate1 = LocalDateTime.of(2021, 06, 30, 23, 59, 59);
        List<BookReview> resultList = queryFactory
                .selectFrom(bookReview)
                .where(bookReview.createDate.between(startDate, endDate).and(bookReview.reviewContents .isNotNull()))
                .fetch();
        return resultList;
    }
}
