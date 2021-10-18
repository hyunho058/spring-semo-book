package com.semobook.bookwant.repository;

import com.semobook.bookwant.domain.BookWant;
import com.semobook.bookwant.dto.Preference;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookWantRepository extends JpaRepository<BookWant,Long> {
//
    //내 선호도 찾기
//    @Query("select r from BookWant r " +
//            "join fetch r.userInfo u " +
//            "join fetch r.book b " +
//            "where u.userNo = :userNo")
//    List<BookWant> findAll(@Param("userNo") long userNo);

    //UserNo별, Isbn별, 선호/비선호
    @Query("select r from BookWant r " +
            "join fetch r.userInfo u " +
            "join fetch r.book b " +
            "where u.userNo = :userNo and b.isbn = :isbn")
    BookWant findAllByUserInfo_UserIdAndBook_Isbn (@Param("userNo") long userNo, @Param("isbn") String isbn);

    @Query(value = "select bw from BookWant bw " +
            "left join fetch bw.userInfo ui " +
            "where ui.userNo = :userNo and bw.preference = :preference"
            ,countQuery = "select count (bw.wantNo) from BookWant bw")
    Page<BookWant> findLikeAllByUserInfo(@Param(value = "userNo") long userNo , Preference preference, Pageable pageable);

    //선호 등록
    BookWant save(BookWant bookWant);
//
//    //선호 삭제
    int deleteBookByWantNo (long wantNo);
}
