package com.semobook.recom.domain;

import com.semobook.book.dto.BookDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class RecomInfo {
    String title;
    List<BookDto> bookInfoList;

    @Builder RecomInfo(String title, List<BookDto> bookInfoList){
        this.title = title;
        this.bookInfoList = bookInfoList;
    }
}
