package com.semobook.bookReview.repository;

import org.springframework.data.repository.query.Param;

public interface BookReviewRepositoryCustom {
    boolean exists(long userNo,String isbn);

    boolean existsByReviewNo(long reviewNo);
}
