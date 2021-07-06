package com.semobook.recom.repository;

import com.semobook.recom.domain.AdminRecom;
import com.semobook.recom.domain.AllReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRecomRepository extends CrudRepository<AdminRecom, Long> {
    List<AdminRecom> findAll();
}
