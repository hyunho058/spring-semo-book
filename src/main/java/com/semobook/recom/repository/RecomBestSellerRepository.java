package com.semobook.recom.repository;

import com.semobook.recom.domain.RecomBestSeller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomBestSellerRepository extends CrudRepository<RecomBestSeller, Integer> {
}
