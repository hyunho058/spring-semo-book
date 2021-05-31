package com.semobook.book.service;

import com.semobook.book.domain.Book;
import com.semobook.book.dto.BookDeleteRequest;
import com.semobook.book.dto.BookListDto;
import com.semobook.book.dto.BookRequest;
import com.semobook.book.dto.BookResponse;
import com.semobook.book.repository.BookRepository;
import com.semobook.common.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        try {
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
        } catch (Exception e) {
            log.info(":: addBook err :: error is {}", e);
            hMessage = "책 저장 실패";
            hCode = StatusEnum.hd1004;
        }

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
        log.info(":: findBook  :: book is {}", isbn);
//        Optional<Book> book = bookRepository.findById(String.valueOf(isbn));
        try {
            Book book = bookRepository.findByIsbn(isbn);

            if (book == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "검색된 도서가 없습니다.";
            } else {
                data = book;
                hCode = StatusEnum.hd1004;
                hMessage = "도서 조회 성공";
            }
        } catch (Exception e) {
            log.info(":: deleteBook err :: error is {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "검색 실패";
        }
        return BookResponse.builder()
                .data(data)
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
    public BookResponse findAll() {
        List<Book> books = bookRepository.findAll();
        List<BookListDto> result = books.stream()
                .map(b -> new BookListDto(b))
                .collect(Collectors.toList());

        return BookResponse.builder()
                .data(result)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }


    /**
     * delete book
     *
     * @author hyunho
     * @since 2021/05/26
     **/
    @Transactional
    public BookResponse deleteBook(String isbn) {
        try {
            log.info(":: deleteBook  :: book is {}", isbn);
            bookRepository.deleteBookByIsbn(isbn);
            hCode = StatusEnum.hd1004;
            hMessage = "삭제 성공";
        } catch (Exception e) {
            log.info(":: deleteBook err :: error is {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "삭제 실패";
        }
        return BookResponse.builder()
                .hCode(hCode)
                .hMessage(hMessage)
                .data(data)
                .build();
    }
}










