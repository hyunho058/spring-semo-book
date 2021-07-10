package com.semobook.qa.repository;

import com.semobook.qa.domain.Qa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QaRepositoryCustom {
    Page qaList(long userNo, Pageable pageable);
}
