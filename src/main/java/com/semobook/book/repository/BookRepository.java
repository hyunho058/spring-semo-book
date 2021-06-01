package com.semobook.book.repository;

import com.semobook.book.domain.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
//@Transactional(readOnly = true)
public interface BookRepository extends CrudRepository<Book, String> {

    @Query("select b from Book b join fetch b.bookReviewList br join fetch br.userInfo")
    Book findByIsbn(String isbn);

    List<Book> findAllByCategory(String category);

    int deleteBookByIsbn (String isbn);

//    @Query("select b from Book b join fetch b.bookReviewList")
    List<Book> findAll();


//    "select o from Order o join fetch o.member m join fetch o.delivery d"
//    @Query("select a from Academy a join fetch a.subjects")
//    List<Academy> findAllJoinFetch();

}