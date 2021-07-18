package com.semobook.bookReview.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semobook.bookReview.domain.BookReview;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.semobook.book.domain.QBook.book;
import static com.semobook.bookReview.domain.QBookReview.bookReview;
import static com.semobook.user.domain.QUserInfo.userInfo;

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
    public List<BookReview> findByBookBetweenDate(long userNo, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("startDate is = {}",startDate);
        List<BookReview> resultList = queryFactory
                .selectFrom(bookReview)
                .join(bookReview.userInfo, userInfo).fetchJoin()    //fetchJoin
                .join(bookReview.book, book).fetchJoin()
                .where(userInfo.userNo.eq(userNo).and(bookReview.createDate.between(startDate, endDate).and(bookReview.reviewContents .isNotNull())))
                .fetch();
        return resultList;
    }

    @Override
    public Page<BookReview> findAllByUserInfo_userNo(long userNo, Pageable pageable) {
        // 조회 쿼리와 카운트 커리 한번에(querydsl 이 아라서 토탈카운트 쿼리를 새성) - 데이터 수가 적을때
//        QueryResults<BookReview> results = queryFactory
//                .select(bookReview)
//                .from(bookReview)
//                .leftJoin(bookReview.userInfo).fetchJoin()
//                .leftJoin(bookReview.book).fetchJoin()
//                .where(bookReview.userInfo.userNo.eq(userNo))
//                .offset(pageable.getOffset())   //N 번부터 시작
//                .limit(pageable.getPageSize()) //조회 갯수
//                .fetchResults();
//
//        List<BookReview> content = result.getResults();
//        long total = result.getTotal();
//
//        return new PageImpl<>(content, pageable, total);


        // 직접 카운트 쿼리 작성(카운트쿼리 최적화할 수 있음 -
        List<BookReview> results = queryFactory
                .select(bookReview)
                .from(bookReview)
                .leftJoin(bookReview.userInfo).fetchJoin()
                .leftJoin(bookReview.book).fetchJoin()
                .where(bookReview.userInfo.userNo.eq(userNo))
                .offset(pageable.getOffset())   //N 번부터 시작
                .limit(pageable.getPageSize()) //조회 갯수
                .fetch();

        long total = queryFactory
                .select(bookReview)
                .from(bookReview)
                .leftJoin(bookReview.userInfo).fetchJoin()
                .leftJoin(bookReview.book).fetchJoin()
                .where(bookReview.userInfo.userNo.eq(userNo))
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }


    @Override
    public Page<BookReview> findByBookReview(String isbn, Pageable pageable) {
        List<BookReview> results = queryFactory
                .selectFrom(bookReview)
                .leftJoin(bookReview.userInfo).fetchJoin()
                .where(bookReview.book.isbn.eq(isbn))
                .orderBy(bookReview.createDate.desc())
                .fetch();

        long total = queryFactory
                .selectFrom(bookReview)
                .where(bookReview.book.isbn.eq(isbn))
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }
}
