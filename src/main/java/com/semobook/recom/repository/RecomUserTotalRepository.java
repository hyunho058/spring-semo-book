package com.semobook.recom.repository;

import com.semobook.recom.domain.RecomUserInfo;
import com.semobook.recom.domain.RecomUserTotal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomUserTotalRepository extends CrudRepository<RecomUserTotal, Integer> {
}
