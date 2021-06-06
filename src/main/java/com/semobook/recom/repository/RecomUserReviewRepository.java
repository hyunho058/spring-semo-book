package com.semobook.recom.repository;

import com.semobook.recom.domain.RecomUserReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomUserReviewRepository extends CrudRepository<RecomUserReview, Long> {
}
