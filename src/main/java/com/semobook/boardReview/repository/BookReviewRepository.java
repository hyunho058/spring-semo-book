package com.semobook.boardReview.repository;

import com.semobook.boardReview.domain.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReview,String> {
    BookReview save(BookReview board);
}
