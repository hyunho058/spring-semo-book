package com.semobook.notice.repository;

import com.semobook.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, String> {
/**
 * Notice Repository
 *
 * @author juhan
 * @since 2021-07-17
**/
    Page<Notice> findAll(Pageable pageable);


}
