package com.semobook.book.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semobook.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.semobook.book.domain.QBook.book;
import static com.semobook.bookReview.domain.QBookReview.bookReview;

public class BookRepositoryImpl implements BookRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public BookRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * ISBN값으로 도서 검색
     *
     * @author hyunho
     * @since 2021/07/29
    **/
    @Override
    public Book findByIsbn(String isbn) {
        return queryFactory
                .selectFrom(book)
                .where(book.isbn.eq(isbn))
                .fetchOne();
    }

    /**
     * ISBN(PK)으로 도서, 도서에 포함된 리뷰 정보 조회
     *
     * @author hyunho
     * @since 2021/07/29
    **/
    @Override
    public Book findByIsbnWithReview(String isbn) {
        return queryFactory
                .selectFrom(book)
                .join(book.bookReviewList, bookReview).fetchJoin()
                .join(bookReview.userInfo).fetchJoin()
                .where(book.isbn.eq(isbn))
                .fetchOne();
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        List<Book> results = queryFactory
                .selectFrom(book)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(book)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

}
