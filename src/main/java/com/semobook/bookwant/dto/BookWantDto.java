package com.semobook.bookwant.dto;

import com.semobook.book.domain.Book;
import com.semobook.bookwant.domain.BookWant;
import lombok.Builder;
import lombok.Data;

@Data
public class BookWantDto {

    private long wantNo;
    private Preference preference;
    private String isbn;


    @Builder
    public BookWantDto(BookWant bookWant){
        wantNo = bookWant.getWantNo();
        preference = bookWant.getPreference();
        isbn = bookWant.getBook().getIsbn();
    }

}
