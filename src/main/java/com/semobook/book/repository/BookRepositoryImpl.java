package com.semobook.book.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semobook.book.domain.Book;
import com.semobook.book.domain.QBook;

import javax.persistence.EntityManager;

import static com.semobook.book.domain.QBook.*;

public class BookRepositoryImpl implements BookRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public BookRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

}
