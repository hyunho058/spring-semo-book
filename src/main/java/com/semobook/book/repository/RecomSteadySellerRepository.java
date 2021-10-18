package com.semobook.book.repository;

import com.semobook.book.domain.RecomBestSeller;
import com.semobook.book.domain.RecomSteadySeller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecomSteadySellerRepository extends CrudRepository<RecomSteadySeller, String> {
}

