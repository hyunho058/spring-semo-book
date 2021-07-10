package com.semobook.qa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semobook.qa.domain.Qa;

public class QaRepositoryImpl implements QaRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public QaRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
