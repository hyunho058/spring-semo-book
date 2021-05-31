package com.semobook.recom.repository;

import com.semobook.recom.domain.RecomUserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomUserInfoRepository extends CrudRepository<RecomUserInfo, Integer> {
}
