package com.semobook.book.repository;

import com.semobook.book.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
//@Transactional(readOnly = true)
public interface BookRepository extends CrudRepository<Book, String> {
    Book findByIsbn(String isbn);
    List<Book> findAllByCategory(String category);
    int deleteBookByIsbn (String isbn);

}