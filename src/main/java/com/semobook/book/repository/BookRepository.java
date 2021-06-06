package com.semobook.book.repository;

import com.semobook.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BookRepository extends CrudRepository<Book, String> {

    @Query(value = "select b from Book b " +
            "left join fetch b.bookReviewList br " +
            "left join fetch br.userInfo where b.isbn = :isbn")
    Book findByIsbn(@Param("isbn") String isbn);

    @Query(value = "select b from Book b left join b.bookReviewList br",
            countQuery = "select count(b.bookName) from Book b")
    Page<Book> findAll(Pageable pageable);

    List<Book> findAllByCategory(String category);
    List<Book> findAllByKeyword(String category);

    Book save(Book book);
    int deleteBookByIsbn (String isbn);


}