package com.semobook.recom.service;

import com.semobook.book.domain.RecomBestSeller;
import com.semobook.book.dto.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResultFilterService {

    /**
     * 중복된 책 제거, 20권이 넘으면 삭제
     *
     * @author hyejinzz
     * @since 2021-06-19
     *@param bookList
     * @return */
    public List<BookDto> BookDtoListCutter(List<BookDto> bookList) {
        bookList = bookList.stream().distinct().collect(Collectors.toList());
        if (bookList.size() > 20) {
            bookList = bookList.subList(0, 20);
        }
        return bookList;
    }

    /**
     * 책 shupple
     *
     * @author hyunho
     * @since 2021/07/11
     **/

    public List<BookDto> bookDtoListShupple(List<BookDto> bookList){
        Collections.shuffle(bookList);
        return bookList;
    }

    /**
     * 책 filter (유저가 싫어요 한 책, 유저가 이미 쓴 책)
     *
     * @author hyunho
     * @since 2021/07/11
     **/
    public void bookListFilter(){

    }
}
