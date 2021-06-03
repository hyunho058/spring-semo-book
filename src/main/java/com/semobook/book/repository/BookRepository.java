package com.semobook.book.repository;

import com.semobook.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
//@Transactional(readOnly = true)
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("select b from Book b join fetch b.bookReviewList br join fetch br.userInfo")
    Book findByIsbn(String isbn);

    List<Book> findAllByCategory(String category);

    Book save(Book bOok);

    int deleteBookByIsbn (String isbn);

//    @Query("select b from Book b join fetch b.bookReviewList")
    List<Book> findAll();

    Page<Book> findALl(Pageable pageable);
//
//    Page<Book> findAll(String isbn, Pageable pageable);


//    "select o from Order o join fetch o.member m join fetch o.delivery d"
//    @Query("select a from Academy a join fetch a.subjects")
//    List<Academy> findAllJoinFetch();

}