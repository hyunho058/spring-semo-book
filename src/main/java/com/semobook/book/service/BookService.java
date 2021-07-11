package com.semobook.book.service;

import com.semobook.book.domain.Book;
import com.semobook.book.dto.*;
import com.semobook.book.repository.BookRepository;
import com.semobook.book.repository.RedisBookRepository;
import com.semobook.common.SemoConstant;
import com.semobook.common.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientRequest;
import tools.StringTools;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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
    private final RedisBookRepository redisBookRepository;


    /**
     * 책 등록
     *
     * @author khh
     * @since 2021/04/25
     **/
    @Transactional
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
            if (book == null) {
                hCode = StatusEnum.hd4444;
                hMessage = "검색된 도서가 없습니다.";
            } else {
                BookDto bookDto = new BookDto(book);
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
            BookWithReviewDto bookWithReviewDto = new BookWithReviewDto(bookRepository.findByIsbnWithReview(isbn));

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
        PageRequest pageAndSortRequest = PageRequest.of(pageNum, 3, Sort.by(Sort.Direction.DESC, "bookName"));
        Page<Book> page = bookRepository.findAll(pageRequest);
//        Slice<Book> page = bookRepository.findAll(pageAndSortRequest);   //limit + 1결과를 반환한다,

        List<Book> content = page.getContent(); //패이지로 가져온
        long totalElements = page.getTotalElements(); //total count
        int pageNumber = page.getNumber();  //page number
        int totalPage = page.getTotalPages();   //total page
        boolean firstPage = page.isFirst(); //first page
        boolean nextPageState = page.hasNext(); //다음 페이지 존재 여부

        log.info("page count = " + content.size());
        log.info("total count = " + totalElements);
        log.info("page number = " + pageNumber);
        log.info("total page = " + totalPage);
        log.info("first page state = " + firstPage);
        log.info("next page state = " + nextPageState);

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

    public BookResponse searchBook(BookSearchRequest bookSearchRequest) {

        hMessage = null;
        hCode = null;
        data = null;
        Mono<DocumentListDto> responseJson;

        try {
            log.info(":: searchBook  :: keyword is {}", bookSearchRequest.getKeyword());

            WebClient webClient = WebClient.builder().baseUrl(SemoConstant.OPEN_API_KAKAO_BOOK).build();
            responseJson = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder.path("v3/search/book")
                            .queryParam("query", bookSearchRequest.getKeyword())
                            .queryParam("page", bookSearchRequest.getPageNum())
                            .queryParam("size", 12)
                            .build()
                    ).header("Authorization", SemoConstant.KAKAO_AK_BOOK_SEARCH)
                    .httpRequest(httpRequest -> {
                        HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
                        reactorRequest.responseTimeout(Duration.ofSeconds(2));
                    })
                    .retrieve()
                    .bodyToMono(DocumentListDto.class);

            responseJson.subscribe(response -> {
                List<Document> documents = new ArrayList<>();
                documents.addAll(response.getDocuments());
                log.info(":: korea book library findBook :: response size is {}", documents.size());
            }, e -> {
                log.info(":: korea book library findBook :: error message is {}", e.getMessage());
            });

            hCode = StatusEnum.hd1004;
            hMessage = "검색 성공";

            return BookResponse.builder()
                    .hCode(hCode)
                    .hMessage(hMessage)
                    .data(responseJson.block())
                    .build();
        } catch (Exception e) {
            log.info(":: searchBook err :: error is {}", e);
            hCode = StatusEnum.hd4444;
            hMessage = "검색 실패";

            return BookResponse.builder()
                    .hCode(hCode)
                    .hMessage(hMessage)
                    .data(null)
                    .build();
        }
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


    /**
     * reids - db - kakaoapi 순서로 책을 조회한다
     *
     * @param isbn 검색할 isbn 입력
     * @author hyejinzz
     * @since 2021/07/10
     */
    public BookDto findBook3Step(String isbn) {
        BookDto bookDto;
        bookDto = findBookUsingRedis(isbn);

        if (bookDto == null) {
            bookDto = findBookUsingDb(isbn);

            if (bookDto != null) {
                saveRedis(bookDto);
            }
        }

        if (bookDto == null) {
            bookDto = findBookUsingApi(isbn);
            if (bookDto != null) {
                saveDatabase(bookDto);
                saveRedis(bookDto);
            }
        }

        return bookDto;
    }


    /**
     * redis에서 책 정보 조회
     *
     * @author hyejinzz
     * @since 2021/07/10
     **/

    public BookDto findBookUsingRedis(String isbn) {
        BookDto bookDto = null;
        try {
            RedisBook redisBook = redisBookRepository.findById(isbn).orElse(null);
            if(redisBook != null){
                bookDto = BookDto.builder()
                        .contents(redisBook.getContents())
                        .publisher(redisBook.getPublisher())
                        .category(redisBook.getCategory())
                        .img(redisBook.getImg())
                        .bookName(redisBook.getBookName())
                        .isbn(redisBook.getIsbn())
                        .author(redisBook.getAuthor())
                        .build();
            }

        } catch (Exception e) {
            log.info(":: searchBook err :: error is {}", e);

        }
        return bookDto;
    }

    /**
     * db에서 책 정보 조회
     *
     * @author hyejinzz
     * @since 2021/07/10
     **/

    public BookDto findBookUsingDb(String isbn) {
        Book book;
        BookDto bookDto = null;
        try {
            book = bookRepository.findByIsbn(isbn);
            if (book == null) {
                log.info(":: findBookUsingDb err :: book is null");
            } else {
                bookDto = new BookDto(book);
            }
        } catch (Exception e) {
            log.error(":: findBookUsingDb err :: error is {}", e);
        }
        return bookDto;
    }

    /**
     * isbn, keyword를 입력하면 카카오 api를 이용해서 책정보를 return하는 메서드
     *
     * @author hyejinzz
     * @since 2021-07-02
     **/
    public BookDto findBookUsingApi(String isbn) {

        Mono<DocumentListDto> responseJson;
        BookDto bookDto = null;
        try {
            log.info(":: searchBook  :: isbn is {}", isbn);

            WebClient webClient = WebClient.builder().baseUrl(SemoConstant.OPEN_API_KAKAO_BOOK).build();
            responseJson = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder.path("v3/search/book")
                            .queryParam("query", isbn)
                            .queryParam("page", 1)
                            .queryParam("size", 1)
                            .build()
                    ).header("Authorization", SemoConstant.KAKAO_AK_BOOK_SEARCH)
                    .httpRequest(httpRequest -> {
                        HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
                        reactorRequest.responseTimeout(Duration.ofSeconds(2));
                    })
                    .retrieve()
                    .bodyToMono(DocumentListDto.class);

            responseJson.subscribe(response -> {
                List<Document> documents = new ArrayList<>();
                documents.addAll(response.getDocuments());
                log.info(":: korea book library findBook :: response size is {}", documents.size());
            }, e -> {
                log.info(":: korea book library findBook :: error message is {}", e.getMessage());
            });


            Document document = responseJson.block().getDocuments().get(0);

            bookDto = BookDto.builder()
                    .author(StringTools.listConvToString(document.getAuthors(), SemoConstant.VARTICAL_BAR))
                    .isbn(chkIsbn(document.getIsbn()))
                    .bookName(document.getTitle())
                    .img(document.getUrl())
                    .publisher(document.getPublisher())
                    .contents(document.getContents())
                    .build();


        } catch (Exception e) {
            log.info(":: searchBook err :: error is {}", e);

        }

        return bookDto;
    }

    /**
     * redis에 저장
     * @param bookDto
     */

    private void saveRedis(BookDto bookDto) {
        RedisBook redisBook = RedisBook.builder()
                .isbn(bookDto.getIsbn())
                .bookName(bookDto.getBookName())
                .author(bookDto.getAuthor())
                .publisher(bookDto.getPublisher())
                .img(bookDto.getImg())
                .contents(bookDto.getContents())
                .build();
        redisBookRepository.save(redisBook);
    }

    /**
     * db에 저장
     * @param bookDto
     */
    private void saveDatabase(BookDto bookDto) {
        Book book = Book.builder()
                .isbn(bookDto.getIsbn())
                .bookName(bookDto.getBookName())
                .author(bookDto.getAuthor())
                .publisher(bookDto.getPublisher())
                .img(bookDto.getImg())
                .contents(bookDto.getContents())
                .build();
        bookRepository.save(book);
    }

    /**
     * isbn이 두개 이상이라면 하나만 넣는다
     *
     * @param isbn
     * @return
     */
    private String chkIsbn(String isbn) {
        if (isbn.length() > 13) {
            List<String> isbnList = StringTools.stringConvToList(isbn, " ");
            return isbnList.get(1);
        }
        return isbn;
    }




}