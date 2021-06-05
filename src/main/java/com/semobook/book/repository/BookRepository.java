package com.semobook.book.repository;

import com.semobook.book.domain.Book;
import com.semobook.user.domain.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
//@Transactional(readOnly = true)
public interface BookRepository extends CrudRepository<Book, String> {
    //search only book
//    @Query("select b from Book b join fetch b.bookReviewList")
    @Query("select b from Book b join fetch b.bookReviewList br")
    Book findByIsbn(String isbn);


    //search book with bookReview
//    @Query("select b from Book b join fetch b.bookReviewList br join fetch br.userInfo")
//    Book findByIsbnWithReview(String isbn);


    List<Book> findAllByCategory(String category);

    Book save(Book book);

    int deleteBookByIsbn (String isbn);

//    @Query("select b from Book b join fetch b.bookReviewList")
//    List<Book> findAll();

    @Query(value = "select b from Book b left join b.bookReviewList br", countQuery = "select count(b.bookName) from Book b")
    Page<Book> findAll(Pageable pageable);
    //Slice<Book> findAll(Pageable pageable); // Slice


//    "select o from Order o join fetch o.member m join fetch o.delivery d"
//    @Query("select a from Academy a join fetch a.subjects")
//    List<Academy> findAllJoinFetch();

}