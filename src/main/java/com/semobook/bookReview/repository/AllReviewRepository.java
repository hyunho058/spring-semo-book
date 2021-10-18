package com.semobook.bookReview.repository;

import com.semobook.bookReview.domain.AllReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AllReviewRepository extends CrudRepository<AllReview, Long> {

}

