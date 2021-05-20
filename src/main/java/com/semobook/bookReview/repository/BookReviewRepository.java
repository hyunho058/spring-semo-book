package com.semobook.bookReview.repository;

import com.semobook.bookReview.domain.BookReview;
import com.semobook.user.domain.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview,String> {
    //저장
    BookReview save(BookReview board);
    //내가 쓴 글 보기
    List<BookReview> findAllByUserNo(UserInfo UserNo, Pageable pageable);
    //이 책 리뷰 모두 보기
    List<BookReview> findAllByIsbn(String Isbn, Pageable pageable);
//    //모든 리뷰 보기
    List<BookReview> findAllByCreateDateBefore(LocalDateTime createDate, Pageable pageable);
    //삭제
    BookReview deleteBookReviewByReviewNo(long reviewNo);


}
