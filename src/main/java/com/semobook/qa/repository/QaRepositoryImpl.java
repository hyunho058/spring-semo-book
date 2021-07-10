package com.semobook.qa.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semobook.qa.domain.QQa;
import com.semobook.qa.domain.Qa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import static com.semobook.qa.domain.QQa.*;
import static com.semobook.user.domain.QUserInfo.*;

public class QaRepositoryImpl implements QaRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public QaRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page qaList(long userNo, Pageable pageable) {
        QueryResults<Qa> results = queryFactory
                .selectFrom(qa)
                .where(qa.userInfo.userNo.eq(userNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(),pageable,results.getTotal());
    }
}
