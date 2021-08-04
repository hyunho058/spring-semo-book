package com.semobook.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semobook.user.domain.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.semobook.user.domain.QUserInfo.userInfo;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<UserInfo> findAll(Pageable pageable) {
        List<UserInfo> results = queryFactory
                .selectFrom(userInfo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .selectFrom(userInfo)
                .fetchCount();
        return new PageImpl<>(results, pageable, totalCount);
    }
}
