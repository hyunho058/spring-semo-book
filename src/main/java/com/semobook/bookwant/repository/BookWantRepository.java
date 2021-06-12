package com.semobook.bookwant.repository;

import com.semobook.bookReview.domain.BookReview;
import com.semobook.bookwant.domain.BookWant;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookWantRepository extends JpaRepository<BookWant,Long> {
//
    //내 선호도 찾기
    @Query("select r from BookWant r " +
            "join fetch r.userInfo u " +
            "join fetch r.book b " +
            "where u.userNo = :userNo")
    List<BookWant> findAll(@Param("userNo") long userNo);

    //UserNo별, Isbn별, 선호/비선호
    @Query("select r from BookWant r " +
            "join fetch r.userInfo u " +
            "join fetch r.book b " +
            "where u.userNo = :userNo and b.isbn = :isbn")
    BookWant findAllByUserInfo_UserIdAndBook_Isbn (@Param("userNo") long userNo, @Param("isbn") String isbn);


    //선호 등록
    BookWant save(BookWant bookWant);
//
//    //선호 삭제
    int deleteBookByWantNo (long wantNo);
}
