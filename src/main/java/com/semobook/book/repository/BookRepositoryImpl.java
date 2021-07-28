package com.semobook.book.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semobook.book.domain.Book;

import javax.persistence.EntityManager;

import static com.semobook.book.domain.QBook.book;

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

}
