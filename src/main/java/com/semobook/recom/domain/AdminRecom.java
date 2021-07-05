package com.semobook.recom.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class AdminRecom {
    @Id
    @Autowired
    private long no;
    private String isbn;
    private String title;

    @Builder
    AdminRecom( String isbn, String title){
        this.isbn = isbn;
        this.title = title;
    }



}
