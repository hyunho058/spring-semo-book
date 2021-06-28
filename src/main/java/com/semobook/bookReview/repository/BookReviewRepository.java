package com.semobook.bookReview.repository;

import com.semobook.book.domain.Book;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.user.domain.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookReviewRepository extends CrudRepository<BookReview, String> {
    //저장
    BookReview save(BookReview board);
    /**
     * 연관관계 설정하면서 findAllByUserInfo(), findAllByBook()수정 하였습니다.
     * JPA 쿼리 요청문에서 FK값 들고오는 부분
     *
     * @author hyunho
     * @since 2021/05/23
     **/
    //find
    //내가 쓴 글 보기
    @Query(value = "select br from BookReview br left join fetch br.userInfo ui left join fetch br.book where ui.userNo = :userNo",
            countQuery = "select count(br.reviewNo) from BookReview  br")
    Page<BookReview> findAllByUserInfo_userNo(@Param(value = "userNo") long userNo, Pageable pageable);

    //이 책 리뷰 모두 보기
//    List<BookReview> findAllByIsbn(String isbn, Pageable pageable);
    List<BookReview> findAllByBook(Book book, Pageable pageable);

    //모든 리뷰 보기
    List<BookReview> findAllByCreateDateBefore(LocalDateTime createDate, Pageable pageable);

    //삭제
//    @Query(value = "select new com.semobook.bookReview.dto.BookReviewDto(br) FROM BookReview br where br.reviewNo = :reviewNo")
    @Query(value = "select br FROM BookReview br where br.reviewNo = :reviewNo")
    int deleteBookReviewByReviewNo(@Param(value = "reviewNo") long reviewNo);

    //리뷰 조회
    BookReview findByReviewNo(long reviewNo);


    /**
     * test select all bookReview data
     * "select o from Order o join fetch o.member m join fetch o.delivery d"
     *
     * @return
     * @Query("select b from Book b join fetch b.bookReviewList")
     * @author hyunho
     * @since 2021/05/30
     */
    Page<BookReview> findAll(Pageable pageable);

    //리뷰 존재 여부 확인
    @Query("select count (br.reviewNo) > 0 " +
            "from BookReview br " +
            "where br.userInfo.userNo = :userNo and br.book.isbn = :isbn")
    boolean exists(@Param(value = "userNo") long userNo,@Param(value = "isbn") String isbn);

    //reviewNo로 존재 여부 확인
    boolean existsByReviewNo(long reviewNo);


    //리뷰 리슽 조회(리뷰 내용을 쓴것만)
    @Query(value = "select br from BookReview br left join fetch br.userInfo ui left join fetch br.book " +
            "where ui.userNo = :userNo " +
            "and br.reviewContents is not null " +
            "and EXTRACT(YEAR FROM c.createdAt)=:ey " +
            "and EXTRACT(MONTH FROM c.createdAt)=:eq\n",
        countQuery = "select count(br.reviewNo) from BookReview  br")
    Page<BookReview> findAllByUserInfo(@Param(value = "userNo") long userNo, Pageable pageable);

//    @Query(value = "select br from BookReview br left join fetch br.userInfo ui left join fetch br.book where ui.userNo = :userNo",
//            countQuery = "select count(br.reviewNo) from BookReview  br")
//    Page<BookReview> findAllByUserInfo_userNo(@Param(value = "userNo") long userNo, Pageable pageable);

}
