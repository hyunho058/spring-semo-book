package com.semobook.book.repository;

import com.semobook.book.dto.RedisBook;
import org.springframework.data.repository.CrudRepository;

public interface RedisBookRepository extends CrudRepository<RedisBook,String> {

}
