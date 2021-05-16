package com.semobook.book.service;

import com.semobook.book.domain.Book;
import com.semobook.book.dto.BookRequest;
import com.semobook.book.dto.BookResponse;
import com.semobook.book.repository.BookRepository;
import com.semobook.common.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    String hMessage;
    StatusEnum hCode;
    Object data;

    private final BookRepository bookRepository;


    /**
     * 책 등록
     *
     * @author khh
     * @since 2021/04/25
    **/
    public BookResponse addBook(BookRequest bookRequest) {
        Book book = bookRepository.save(Book.builder()
                .isbn(bookRequest.getIsbn())
                .bookName(bookRequest.getBookName())
                .author(bookRequest.getAuthor())
                .publisher(bookRequest.getPublisher())
                .kdc(bookRequest.getKdc())
                .category(bookRequest.getCategory())
                .keyword(bookRequest.getKeyword())
                .img(bookRequest.getImg())
                .build());

        hMessage = "생성완료";
        hCode = StatusEnum.hd1004;
        data = book;

        return BookResponse.builder()
                .hCode(hCode)
                .hMessage(hMessage)
                .data(data)
                .build();
    }

    /**
     * 도서 조회
     *
     * @author khh
     * @since 2021/04/25
     **/
    public BookResponse findBook(String isbn) {
        log.info("TEST================START {}", isbn);
//        Optional<Book> book = bookRepository.findById(String.valueOf(isbn));
        Book book = bookRepository.findByIsbn(isbn);
        log.info("TEST================END {}",book);
        return BookResponse.builder()
                .data(book)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }


    /**
     * 전체 도서 조회
     *
     * @author khh
     * @since 2021/04/25
    **/
    public BookResponse findAll(){
        Iterable<Book> books = bookRepository.findAll();
        return BookResponse.builder()
                .data(books)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }
}










