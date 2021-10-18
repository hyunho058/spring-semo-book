package com.semobook.book.repository;

import com.semobook.book.domain.RecomBestSeller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecomBestSellerRepository extends CrudRepository<RecomBestSeller, String> {
}

