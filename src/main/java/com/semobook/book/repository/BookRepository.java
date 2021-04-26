package com.semobook.book.repository;

import com.semobook.book.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    Book findByIsbn(Long isbn);
}