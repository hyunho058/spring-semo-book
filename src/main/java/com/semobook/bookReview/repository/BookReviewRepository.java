package com.semobook.bookReview.repository;

import com.semobook.book.domain.Book;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 연관관계 설정하면서 findAllByUserInfo(), findAllByBook()수정 하였습니다.
     * JPA 쿼리 요청문에서 FK값 들고오는 부분
     *
     * @author hyunho
     * @since 2021/05/23
    **/
    //find
    //내가 쓴 글 보기
//    List<BookReview> findAllByUserNo(UserInfo userNo, Pageable pageable);
    List<BookReview> findAllByUserInfo(UserInfo userInfo, Pageable pageable);
    //이 책 리뷰 모두 보기
//    List<BookReview> findAllByIsbn(String isbn, Pageable pageable);
    List<BookReview> findAllByBook(Book book, Pageable pageable);
    ////////////////////////////////////////////////////////////////////////////////////////////////////

//    //모든 리뷰 보기
    List<BookReview> findAllByCreateDateBefore(LocalDateTime createDate, Pageable pageable);
    //삭제
    BookReview deleteBookReviewByReviewNo(long reviewNo);

    BookReview findByReviewNo(long reviewNo);

    List<BookReview> findAll();



}
