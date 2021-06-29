package com.semobook.bookReview.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semobook.book.domain.Book;
import com.semobook.book.domain.QBook;
import com.semobook.bookReview.domain.QBookReview;
import jdk.internal.net.http.common.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;

import static com.semobook.book.domain.QBook.*;
import static com.semobook.bookReview.domain.QBookReview.bookReview;

@Slf4j
public class BookReviewRepositoryImpl implements BookReviewRepositoryCustom{
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
}
