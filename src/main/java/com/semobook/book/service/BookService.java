package com.semobook.book.service;

import com.semobook.book.domain.Book;
import com.semobook.book.dto.*;
import com.semobook.book.repository.BookRepository;
import com.semobook.bookReview.domain.BookReview;
import com.semobook.common.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

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
        hMessage = null;
        hCode = null;
        data = null;
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

        hMessage = null;
        hCode = null;
        data = null;

        try {
            Book book = bookRepository.findByIsbn(isbn);
            BookDto bookDto =  new BookDto(book);

            if (book == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "검색된 도서가 없습니다.";
            } else {
                data = bookDto;
                hCode = StatusEnum.hd1004;
                hMessage = "도서 조회 성공";
            }
        } catch (Exception e) {
            log.info(":: findBook err :: error is {}", e);
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
     *
     *
     * @author hyunho
     * @since 2021/06/05
    **/
    public BookResponse findBookWithReview(String isbn) {
        log.info(":: findBookWithReview  :: book is {}", isbn);
        hMessage = null;
        hCode = null;
        data = null;
        try {
//            Book book = bookRepository.findByIsbnWithReview(isbn);
            BookWithReviewDto bookWithReviewDto =  new BookWithReviewDto(bookRepository.findByIsbnWithReview(isbn));

            if (bookWithReviewDto == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "검색된 도서가 없습니다.";
            } else {
                data = bookWithReviewDto;
                hCode = StatusEnum.hd1004;
                hMessage = "도서 조회 성공";
            }
        } catch (Exception e) {
            log.info("findBookWithReview :: deleteBook err :: error is {}", e);
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
    public BookResponse findAll(int pageNum) {

        hMessage = null;
        hCode = null;
        data = null;

        //page처리 적용
        PageRequest pageRequest = PageRequest.of(pageNum, 5);
        Page<Book> page = bookRepository.findAll(pageRequest);
//        Slice<Book> slicePage = bookRepository.findAll(pageRequest);  client 단에ㅓ 더보기 기능을 사용할때 slice 를 사용하면 좋다.
        List<BookListDto> result = page.getContent().stream()
                .map(b -> new BookListDto(b))
                .collect(Collectors.toList());



        return BookResponse.builder()
                .data(result)
                .hCode(hCode)
                .hMessage(hMessage)
                .build();
    }

    /**
     * Book List Page
     *
     * @author hyunho
     * @since 2021/06/03
    **/
//    public BookResponse pageBookList(){
//        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "bookName"));
//        Page<Book> books = bookRepository.findALl(pageRequest);
//
//        List<Book> content = books.getContent();
//
////        List<BookListDto> result = books.stream()
////                .map(b -> new BookListDto(b))
////                .collect(Collectors.toList());
//
//        return BookResponse.builder()
//                .data(content)
//                .hCode(hCode)
//                .hMessage(hMessage)
//                .build();
//    }


    /**
     * delete book
     *
     * @author hyunho
     * @since 2021/05/26
     **/
    @Transactional
    public BookResponse deleteBook(String isbn) {

        hMessage = null;
        hCode = null;
        data = null;

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


    /**
     * book 패이징 처리 with bookReview
     *
     * @author hyunho
     * @since 2021/06/02
    **/
//    public BookResponse findAllWithReview(@RequestParam(value = "offset", defaultValue = "0") int offset,
//                                          @RequestParam(value = "limit", defaultValue = "100") int limit)
//    {
//        List<Book> books = bookRepository.findAll();
//        List<BookWithReviewDto> result = books.stream()
//                .map(b -> new BookWithReviewDto(b))
//                .collect(Collectors.toList());
//
//        return BookResponse.builder()
//                .data(result)
//                .hCode(hCode)
//                .hMessage(hMessage)
//                .build();
//    }


}










