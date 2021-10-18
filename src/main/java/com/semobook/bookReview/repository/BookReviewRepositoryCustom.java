package com.semobook.bookReview.repository;

import com.semobook.bookReview.domain.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface BookReviewRepositoryCustom {
    boolean exists(long userNo,String isbn);

    boolean existsByReviewNo(long reviewNo);


    int countfindByBookBetweenDate(long userNo, LocalDateTime startDate, LocalDateTime endDate);

    int countReview (long userNo);

    List<BookReview> findByBookBetweenDate(long userNo, LocalDateTime startDate, LocalDateTime endDate);

    Page<BookReview> findAllByUserInfo_userNo(long userNo, Pageable pageable);

    Page<BookReview> findByBookReview(String isbn, Pageable pageable);

    BookReview findByReviewNo(long reviewNo);;

    Page<BookReview> findAllByUserInfoAndNotNullContents(long userNo, Pageable pageable);


}
