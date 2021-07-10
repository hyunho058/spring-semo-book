package com.semobook.qa.repository;

import com.semobook.qa.domain.Qa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QaRepository extends JpaRepository<Qa, Long>, QaRepositoryCustom {

}
