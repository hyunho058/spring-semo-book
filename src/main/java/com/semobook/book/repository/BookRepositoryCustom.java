package com.semobook.book.repository;

import com.semobook.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    //ISBN(PK) 으로 도서 종보 조회
    Book findByIsbn(String isbn);

    //ISBN(PK) 으로 도서, 도서에 포함된 리뷰 종보 조회
    Book findByIsbnWithReview(String isbn);

    //책 전채 조회(패이징처리)//
    Page<Book> findAll(Pageable pageable);    //책 전채 조회(패이징처리)//


    Page<Book> findAllByCategory(Pageable pageable,String category);
//

    //책 제거
    long deleteBookByIsbn (String isbn);

    //도서 존재 여부
    boolean existsByIsbn(String isbn);

    long existCount(String isbn);
}
