package com.semobook.book.dto;

import com.semobook.book.domain.Book;
import com.semobook.bookReview.dto.BookReviewWithBookDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BookDto {

    private String isbn;
    private String bookName;
    private String author;
    private String publisher;
    private String kdc;
    private String category;
    private String keyword;
    private String img;

    public BookDto(Book book){
        isbn = book.getIsbn();
        bookName = book.getBookName();
        author = book.getAuthor();
        publisher = book.getPublisher();
        kdc = book.getKdc();
        category = book.getCategory();
        keyword = book.getKeyword();
        img = book.getImg();
    }

       @Builder
        public BookDto(String isbn, String bookName, String author, String publisher,
                       String kdc, String category, String keyword, String img){
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.kdc = kdc;
        this. category = category;
        this.keyword = keyword;
        this.img = img;
    }







//    public BookReviewDto(BookReview bookReview) {
//        rating = bookReview.getRating();
//        reviewContents = bookReview.getReviewContents();
//        createDate = bookReview.getCreateDate();
//        declaration = bookReview.getDeclaration();
//        isbn = bookReview.getBook().getIsbn();
//        userName = bookReview.getUserInfo().getUserName();

}
