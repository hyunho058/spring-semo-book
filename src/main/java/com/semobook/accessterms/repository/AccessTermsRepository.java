package com.semobook.accessterms.repository;

import com.semobook.accessterms.domain.Accessterms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTermsRepository extends JpaRepository<Accessterms, String> {
    /**
     * Accessterms Repository
     *
     * @author juhan
     * @since 2021-07-17
    **/
    Accessterms findByAccessNo(long accessNo);
}
