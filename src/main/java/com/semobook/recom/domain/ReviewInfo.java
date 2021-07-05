package com.semobook.recom.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewInfo {
    String isbn;
    String category;
    int point;


    @Builder
    public ReviewInfo(String isbn, String category, int point){
        this.isbn = isbn;
        this.category = category;
        this.point = point;
    }

}


