package com.semobook.recom.repository;

import com.semobook.recom.domain.AllReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AllReviewRepository extends CrudRepository<AllReview, Long> {

}

