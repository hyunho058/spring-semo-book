package com.semobook.recom.repository;

import com.semobook.recom.domain.UserPriorityRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserPriorityRedisRepository extends CrudRepository<UserPriorityRedis, Long> {


}