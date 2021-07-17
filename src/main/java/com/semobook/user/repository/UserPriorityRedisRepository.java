package com.semobook.user.repository;

import com.semobook.user.domain.UserPriorityRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserPriorityRedisRepository extends CrudRepository<UserPriorityRedis, Long> {


}