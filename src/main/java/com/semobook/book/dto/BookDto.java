package com.semobook.book.dto;

import com.semobook.book.domain.Book;
import com.semobook.bookReview.dto.BookReviewWithBookDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookDto {

    private String isbn;
    private String bookName;
    private String author;
    private String publisher;
    private String kdc;
    private String category;
    private String keyword;
    private String img;
    private List<BookReviewWithBookDto> bookReviews;


    public BookDto(Book book){
        isbn = book.getIsbn();
        bookName = book.getBookName();
        author = book.getAuthor();
        publisher = book.getPublisher();
        kdc = book.getKdc();
        category = book.getCategory();
        keyword = book.getKeyword();
        img = book.getImg();
        bookReviews = book.getBookReviewList().stream()
                .map(bookReview -> new BookReviewWithBookDto(bookReview))
                .collect(Collectors.toList());
    }





//    public BookReviewDto(BookReview bookReview) {
//        rating = bookReview.getRating();
//        reviewContents = bookReview.getReviewContents();
//        createDate = bookReview.getCreateDate();
//        declaration = bookReview.getDeclaration();
//        isbn = bookReview.getBook().getIsbn();
//        userName = bookReview.getUserInfo().getUserName();

}
